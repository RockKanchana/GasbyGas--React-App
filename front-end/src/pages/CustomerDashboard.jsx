// src/pages/CustomerDashboard.jsx
import { useState, useEffect } from "react";
import Navbar from "../components/ClientNav";
import { motion } from "framer-motion";
import CustomerService from '../services/CustomerService';
import { useParams } from "react-router-dom";
import { Tooltip, IconButton, Dialog, DialogTitle, DialogContent, DialogActions, Button } from "@mui/material";
import DeleteIcon from '@mui/icons-material/Delete';
import VisibilityIcon from "@mui/icons-material/Visibility";

const CustomerDashboard = () => {

  const [gasRequest, setGasRequest] = useState({
    district: "",
    outlet: "",
    gasId: "",
    quantity: 1
  });

  const [outlets, setOutlets] = useState([]);
  const [orders, setOrders] = useState([]);
  const [customer, setCustomer] = useState([]);
  const { email } = useParams();

  const [token, setToken] = useState(null);
  const [open, setOpen] = useState(false);


  const loadCustomer = async () => {
    try {
      let res = await CustomerService.fetchCustomer(email);
      if (res.status === 200) {
        setCustomer(res.data.data);
      } else {
        console.log("Fetching error: " + res);
      }
    } catch (error) {
      console.log("Error fetching outlets:", error);
    }
  };

  const loadOutletList = async () => {
    try {
      let res = await CustomerService.fetchOutlets();
      if (res.status === 200) {
        setOutlets(res.data.data);
      } else {
        console.log("Fetching error: " + res);
      }
    } catch (error) {
      console.log("Error fetching outlets:", error);
    }
  };

  const loadRequestList = async (clientId) => {
    if (!clientId) return;
    try {
      let res = await CustomerService.fetchRequests(clientId);
      if (res.status === 200) {
        setOrders(res.data.data);
      } else {
        console.log("Fetching error: " + res);
      }
    } catch (error) {
      console.log("Error fetching reuests:", error);
    }
  };

  useEffect(() => {
    loadOutletList();
    loadCustomer();
  }, [email]);

  useEffect(() => {
    if (customer?.clientId) {
      loadRequestList(customer.clientId);
    }
  }, [customer]);


  const handleOpen = async (order) => {
    try {
      let res = await CustomerService.fetchToken(order.requestId);
      if (res.status === 200) {
        setToken(res.data.data || {});
        setOpen(true);
      } else {
        console.log("Fetching error: " + res);
      }
    } catch (error) {
      console.error("Error fetching token details:", error);
    }
  };

  const handleClose = () => {
    setOpen(false);
    setToken(null);
  };


  const [isNotificationPopupOpen, setIsNotificationPopupOpen] = useState(false);
  const [notifications, setNotifications] = useState([
    {
      id: 1,
      message: "Your gas request has been approved.",
      date: "2023-10-01",
      read: false,
    }
  ]);


  const handleGasRequest = async (e) => {
    e.preventDefault(); 2
    try {
      const data = {
        clientId: parseInt(customer.clientId),
        outletId: parseInt(gasRequest.outlet),
        status: "PENDING",
        requestList: [
          {
            requestId: 0,
            gasId: parseInt(gasRequest.gasId),
            quantity: parseInt(gasRequest.quantity)
          }
        ]
      };
      console.log("data ", data);
      const res = await CustomerService.postRequest(data);
      if (res.status === 200) {
        alert(res.data.message);
        loadRequestList(customer.clientId);
        setGasRequest({  district: "", outlet: "", gasId: "" });
        console.log("Request successful");
      } else {
        console.log("Error Message:", res.data.message);
        alert(res.data.message);
      }
    } catch (error) {
      console.log("Server Error Message:", error.response.data.message);
      alert(error.response.data.message);
    }
  };


  return (
    <div className="min-h-screen bg-gray-900 text-white">
      {/* Navbar */}
      <Navbar />

      <div className="relative">
        <button
          className="absolute top-4 right-4 text-blue-500 hover:text-blue-400"
          onClick={() => setIsNotificationPopupOpen(!isNotificationPopupOpen)}
        >
          🔔
        </button>
        {isNotificationPopupOpen && (
          <div className="absolute top-12 right-4 bg-gray-800 p-4 rounded-lg shadow-lg w-80 z-50">
            <h3 className="text-lg font-semibold mb-2">Notifications</h3>
            {notifications.length > 0 ? (
              <ul className="space-y-2">
                {notifications.map((notification) => (
                  <li
                    key={notification.id}
                    className={`p-2 rounded ${!notification.read ? "bg-gray-700" : ""
                      }`}
                  >
                    <p className="text-gray-300">{notification.message}</p>
                    <p className="text-gray-400 text-sm">{notification.date}</p>
                    {!notification.read && (
                      <button
                        className="text-blue-500 hover:underline mt-1"
                        onClick={() =>
                          setNotifications((prev) =>
                            prev.map((n) =>
                              n.id === notification.id ? { ...n, read: true } : n
                            )
                          )
                        }
                      >
                        Mark as Read
                      </button>
                    )}
                  </li>
                ))}
              </ul>
            ) : (
              <p className="text-gray-400">No notifications available.</p>
            )}
          </div>
        )}
      </div>

      <motion.div
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ duration: 1 }}
        className="p-8"
      >
        <h1 className="text-3xl font-bold mb-8">Customer Dashboard</h1>

        <div className="bg-gray-800 p-6 rounded-lg shadow-lg w-full max-w-md mx-auto mb-8">
          <h2 className="text-xl font-semibold mb-4">Request Gas</h2>
          <form onSubmit={handleGasRequest} className="space-y-4">
            <label className="block text-gray-300">District</label>
            <select
              value={gasRequest.district}
              onChange={(e) =>
                setGasRequest({ ...gasRequest, district: e.target.value })
              }
              className="w-full px-4 py-2 bg-gray-700 text-white rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
               <option value="">Select District</option>
              {outlets.map((outlet) => (
                <option key={outlet.district} value={outlet.district}>{outlet.district}</option>
              ))}
            </select>

            <label className="block text-gray-300">Outlet</label>
            <select
              value={gasRequest.outlet}
              onChange={(e) => {
                console.log("Selected Outlet ID:", e.target.value);
                setGasRequest({ ...gasRequest, outlet: e.target.value });
              }}
              className="w-full px-4 py-2 bg-gray-700 text-white rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
              <option value="">Select Outlet</option>
              {outlets.map((outlet) => (
                <option key={outlet.outletId} value={outlet.outletId}>{outlet.outletName}</option>
              ))}
            </select>
            <label className="block text-gray-300">Gas Type</label>
            <select
              value={gasRequest.gasId}
              onChange={(e) => {
                console.log("Selected Gas Type:", e.target.value);
                setGasRequest({ ...gasRequest, gasId: String(e.target.value) });
              }}
              className="w-full px-4 py-2 bg-gray-700 text-white rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
              <option value="">Select a Gas Type</option>
              <option value="4">37.5Kg</option>
              <option value="1">12.5Kg</option>
              <option value="2">5Kg</option>
              <option value="3">2.5Kg</option>
            </select>
            <Button
              className="bg-blue-700 w-full"
              type="submit"
            >
              Submit Request
            </Button>
          </form>
        </div>

        {/* My Orders Table */}
        <div className="mb-8">
          <h2 className="text-xl font-semibold mb-4">My Orders</h2>
          <table className="w-full bg-gray-800 rounded-lg overflow-hidden">
            <thead>
              <tr className="bg-gray-700">
                <th className="p-4 text-left">Order ID</th>
                <th className="p-4 text-left">Outlet Name</th>
                <th className="p-4 text-left">Request Date</th>
                <th className="p-4 text-left">Gas Type</th>
                <th className="p-4 text-left">Price</th>
                <th className="p-4 text-left">Status</th>
                <th className="p-4 text-left">Action</th>
              </tr>
            </thead>
            <tbody>
              {orders.map((order) => (
                <tr key={order.id} className="border-b border-gray-700">
                  <td className="p-4">{order.requestId}</td>
                  <td className="p-4">{order.outletName}</td>
                  <td className="p-4">{order.requestDate}</td>
                  <td className="p-4">{order.resDetails[0].gasType}</td>
                  <td className="p-4">{order.resDetails[0].gasPrice + ".00"}</td>
                  <td className="p-4">{order.status}</td>
                  <td className="p-4">
                    <Tooltip title="View Details">
                      <IconButton onClick={() => handleOpen(order)}>
                        <VisibilityIcon color="primary" />
                      </IconButton>
                    </Tooltip>
                    <Dialog open={open} onClose={handleClose}>
                      <DialogTitle className="text-2xl font-extrabold text-gray-900 bg-gray-200 px-6 py-4 rounded-t-lg border-b">
                        Gas Token Details
                      </DialogTitle>
                      <DialogContent className="p-10 mt-4">
                        {token ? (
                          <div className="space-y-3">
                            <p className="text-gray-600">
                              <span className="font-medium text-gray-800">Token ID:</span> {token.tokenId || "N/A"}
                            </p>
                            <p className="text-gray-600">
                              <span className="font-medium text-gray-800">Pickup Date:</span> {token.pickupDate || "N/A"}
                            </p>
                            <p className="text-gray-600">
                              <span className="font-medium text-gray-800">Expiry Date:</span> {token.expiryDate || "N/A"}
                            </p>
                            <p className="text-gray-600">
                              <span className="font-medium text-gray-800">Status:</span>
                              <span className={`ml-2 px-2 py-1 rounded-full text-white text-sm 
                                ${token.status === "ACTIVE" ? "bg-green-500" : token.status === "COMPLETED" ? "bg-blue-500" : "bg-red-500"}`}>
                                {token.status || "N/A"}
                              </span>
                            </p>
                          </div>
                        ) : (
                          <p className="text-gray-500">Loading token details...</p>
                        )}
                      </DialogContent>
                      <DialogActions className="px-6 pb-4">
                        <Button onClick={handleClose} className="text-blue-500 hover:text-blue-800">
                          Close
                        </Button>
                      </DialogActions>
                    </Dialog>
                    <Tooltip title="Cancel Request">
                      <IconButton
                        onClick={() => {
                          console.log("cancel icon clicked!")
                        }}
                      >
                        <DeleteIcon color="error" />
                      </IconButton>
                    </Tooltip>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </motion.div>
    </div>
  );
};


export default CustomerDashboard;
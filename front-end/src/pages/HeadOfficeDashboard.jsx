// src/pages/HeadOfficeDashboard.jsx
import { useState } from "react";
import Navbar from "../components/Navbar";
import Button from "../components/Button";
import { motion } from "framer-motion";

const HeadOfficeDashboard = () => {
  // Dummy Data for Pending Delivery Requests
  const [pendingRequests, setPendingRequests] = useState([
    {
      id: 1,
      district: "District A",
      outletName: "Outlet 1",
      "37.5Kg": 10,
      "12.5Kg": 20,
      "5Kg": 5,
      "2.5Kg": 0,
      orderDate: "2023-10-01",
    },
    {
      id: 2,
      district: "District B",
      outletName: "Outlet 2",
      "37.5Kg": 5,
      "12.5Kg": 10,
      "5Kg": 0,
      "2.5Kg": 0,
      orderDate: "2023-10-02",
    },
  ]);

  // Dummy Data for Approved Delivery Requests
  const [approvedRequests, setApprovedRequests] = useState([
    {
      id: 1,
      district: "District A",
      outletName: "Outlet 1",
      "37.5Kg": 10,
      "12.5Kg": 20,
      "5Kg": 5,
      "2.5Kg": 0,
      orderDate: "2023-10-01",
      deliveryDate: "2023-10-10",
      remainingDays: 5,
      status: "Scheduled",
    },
    {
      id: 2,
      district: "District B",
      outletName: "Outlet 2",
      "37.5Kg": 5,
      "12.5Kg": 10,
      "5Kg": 0,
      "2.5Kg": 0,
      orderDate: "2023-10-02",
      deliveryDate: "2023-10-12",
      remainingDays: 3,
      status: "Delivered",
    },
  ]);

  // Dummy Data for Business Account Approvals
  const [businessAccounts, setBusinessAccounts] = useState([
    {
      id: 1,
      registrationNumber: "REG123",
      businessName: "ABC Restaurant",
      phone: "1234567890",
      email: "abc@example.com",
      address: "Address 1",
      certification: "Certification.pdf",
      status: "Pending",
    },
    {
      id: 2,
      registrationNumber: "REG456",
      businessName: "XYZ Manufacturing",
      phone: "0987654321",
      email: "xyz@example.com",
      address: "Address 2",
      certification: "Certification.pdf",
      status: "Pending",
    },
  ]);

  // Dummy Data for Business Gas Orders
  const [businessOrders, setBusinessOrders] = useState([
    {
      id: 1,
      "37.5Kg": 20,
      "12.5Kg": 50,
      "5Kg": 10,
      "2.5Kg": 0,
      totalPrice: "Rs. 2,00,000",
      orderDate: "2023-10-01",
      status: "Pending",
    },
    {
      id: 2,
      "37.5Kg": 10,
      "12.5Kg": 20,
      "5Kg": 5,
      "2.5Kg": 0,
      totalPrice: "Rs. 1,00,000",
      orderDate: "2023-10-02",
      status: "Pending",
    },
  ]);

  // Dummy Data for Approved Business Gas Orders
  const [approvedBusinessOrders, setApprovedBusinessOrders] = useState([
    {
      id: 1,
      "37.5Kg": 20,
      "12.5Kg": 50,
      "5Kg": 10,
      "2.5Kg": 0,
      totalPrice: "Rs. 2,00,000",
      orderDate: "2023-10-01",
      deliveryDate: "2023-10-10",
      remainingDays: 5,
      status: "Scheduled",
    },
    {
      id: 2,
      "37.5Kg": 10,
      "12.5Kg": 20,
      "5Kg": 5,
      "2.5Kg": 0,
      totalPrice: "Rs. 1,00,000",
      orderDate: "2023-10-02",
      deliveryDate: "2023-10-12",
      remainingDays: 3,
      status: "Delivered",
    },
  ]);

  // Dummy Data for Outlet Stock Levels
  const outlets = [
    {
      id: 1,
      name: "Outlet 1",
      stock: { "37.5Kg": 50, "12.5Kg": 120, "5Kg": 80, "2.5Kg": 200 },
    },
    {
      id: 2,
      name: "Outlet 2",
      stock: { "37.5Kg": 30, "12.5Kg": 100, "5Kg": 60, "2.5Kg": 150 },
    },
  ];

  // Dummy Data for Outlet Bulk Orders
  const bulkOrders = [
    {
      id: 1,
      "37.5Kg": 10,
      "12.5Kg": 20,
      "5Kg": 5,
      "2.5Kg": 0,
      orderDate: "2023-10-01",
      deliveryDate: "2023-10-10",
      remainingDays: 5,
      status: "Scheduled",
    },
    {
      id: 2,
      "37.5Kg": 5,
      "12.5Kg": 10,
      "5Kg": 0,
      "2.5Kg": 0,
      orderDate: "2023-10-02",
      deliveryDate: "2023-10-12",
      remainingDays: 3,
      status: "Delivered",
    },
  ];

  // State for Popups
  const [isOrderDetailsPopupOpen, setIsOrderDetailsPopupOpen] = useState(false);
  const [selectedOrder, setSelectedOrder] = useState(null);
  const [deliveryDate, setDeliveryDate] = useState("");

  const [selectedOutletId, setSelectedOutletId] = useState(1);
  const [selectedDistrict, setSelectedDistrict] = useState("");

  // Handle Order Approval
  const handleOrderApproval = (id, action) => {
    setPendingRequests((prevRequests) =>
      prevRequests.map((request) =>
        request.id === id ? { ...request, status: action } : request
      )
    );
    setIsOrderDetailsPopupOpen(false);
  };

  return (
    <div className="min-h-screen bg-gray-900 text-white">
      <Navbar />
      <motion.div
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ duration: 1 }}
        className="p-8"
      >
        <h1 className="text-3xl font-bold mb-8">Head Office Dashboard</h1>

        {/* Outlets Delivery Schedule Requests - Pending */}
        <div className="mb-8">
          <h2 className="text-xl font-semibold mb-4">Pending Delivery Requests</h2>
          <table className="w-full bg-gray-800 rounded-lg overflow-hidden">
            <thead>
              <tr className="bg-gray-700">
                <th className="p-4 text-left">Order ID</th>
                <th className="p-4 text-left">District</th>
                <th className="p-4 text-left">Outlet Name</th>
                <th className="p-4 text-left">37.5Kg</th>
                <th className="p-4 text-left">12.5Kg</th>
                <th className="p-4 text-left">5Kg</th>
                <th className="p-4 text-left">2.5Kg</th>
                <th className="p-4 text-left">Order Date</th>
                <th className="p-4 text-left">Actions</th>
              </tr>
            </thead>
            <tbody>
              {pendingRequests.map((request) => (
                <tr key={request.id} className="border-b border-gray-700">
                  <td className="p-4">{request.id}</td>
                  <td className="p-4">{request.district}</td>
                  <td className="p-4">{request.outletName}</td>
                  <td className="p-4">{request["37.5Kg"]}</td>
                  <td className="p-4">{request["12.5Kg"]}</td>
                  <td className="p-4">{request["5Kg"]}</td>
                  <td className="p-4">{request["2.5Kg"]}</td>
                  <td className="p-4">{request.orderDate}</td>
                  <td className="p-4">
                    <button
                      className="text-blue-500 hover:underline"
                      onClick={() => {
                        setSelectedOrder(request);
                        setIsOrderDetailsPopupOpen(true);
                      }}
                    >
                      View
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        {/* Outlets Delivery Schedule Requests - Approved */}
        <div className="mb-8">
          <h2 className="text-xl font-semibold mb-4">Approved Delivery Requests</h2>
          <table className="w-full bg-gray-800 rounded-lg overflow-hidden">
            <thead>
              <tr className="bg-gray-700">
                <th className="p-4 text-left">Order ID</th>
                <th className="p-4 text-left">District</th>
                <th className="p-4 text-left">Outlet Name</th>
                <th className="p-4 text-left">37.5Kg</th>
                <th className="p-4 text-left">12.5Kg</th>
                <th className="p-4 text-left">5Kg</th>
                <th className="p-4 text-left">2.5Kg</th>
                <th className="p-4 text-left">Order Date</th>
                <th className="p-4 text-left">Delivery Date</th>
                <th className="p-4 text-left">Remaining Days</th>
                <th className="p-4 text-left">Status</th>
              </tr>
            </thead>
            <tbody>
              {approvedRequests.map((request) => (
                <tr key={request.id} className="border-b border-gray-700">
                  <td className="p-4">{request.id}</td>
                  <td className="p-4">{request.district}</td>
                  <td className="p-4">{request.outletName}</td>
                  <td className="p-4">{request["37.5Kg"]}</td>
                  <td className="p-4">{request["12.5Kg"]}</td>
                  <td className="p-4">{request["5Kg"]}</td>
                  <td className="p-4">{request["2.5Kg"]}</td>
                  <td className="p-4">{request.orderDate}</td>
                  <td className="p-4">{request.deliveryDate}</td>
                  <td className="p-4">{request.remainingDays} days</td>
                  <td className="p-4">{request.status}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        {/* Business Account Approvals */}
        <div className="mb-8">
          <h2 className="text-xl font-semibold mb-4">Business Account Approvals</h2>
          <table className="w-full bg-gray-800 rounded-lg overflow-hidden">
            <thead>
              <tr className="bg-gray-700">
                <th className="p-4 text-left">Business Registration Number</th>
                <th className="p-4 text-left">Business Name</th>
                <th className="p-4 text-left">Phone Number</th>
                <th className="p-4 text-left">Email</th>
                <th className="p-4 text-left">Address</th>
                <th className="p-4 text-left">Certification</th>
                <th className="p-4 text-left">Actions</th>
              </tr>
            </thead>
            <tbody>
              {businessAccounts.map((account) => (
                <tr key={account.id} className="border-b border-gray-700">
                  <td className="p-4">{account.registrationNumber}</td>
                  <td className="p-4">{account.businessName}</td>
                  <td className="p-4">{account.phone}</td>
                  <td className="p-4">{account.email}</td>
                  <td className="p-4">{account.address}</td>
                  <td className="p-4">{account.certification}</td>
                  <td className="p-4 flex space-x-2">
                    <Button
                      className="bg-green-500 px-2 py-1 rounded"
                      onClick={() => {
                        setBusinessAccounts((prevAccounts) =>
                          prevAccounts.map((acc) =>
                            acc.id === account.id ? { ...acc, status: "Approved" } : acc
                          )
                        );
                      }}
                    >
                      Approve
                    </Button>
                    <Button
                      className="bg-red-500 px-2 py-1 rounded"
                      onClick={() => {
                        setBusinessAccounts((prevAccounts) =>
                          prevAccounts.map((acc) =>
                            acc.id === account.id ? { ...acc, status: "Rejected" } : acc
                          )
                        );
                      }}
                    >
                      Reject
                    </Button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        {/* Business Gas Orders */}
        <div className="mb-8">
          <h2 className="text-xl font-semibold mb-4">Business Gas Orders</h2>
          <table className="w-full bg-gray-800 rounded-lg overflow-hidden">
            <thead>
              <tr className="bg-gray-700">
                <th className="p-4 text-left">Order ID</th>
                <th className="p-4 text-left">37.5Kg</th>
                <th className="p-4 text-left">12.5Kg</th>
                <th className="p-4 text-left">5Kg</th>
                <th className="p-4 text-left">2.5Kg</th>
                <th className="p-4 text-left">Total Price</th>
                <th className="p-4 text-left">Order Date</th>
                <th className="p-4 text-left">Actions</th>
              </tr>
            </thead>
            <tbody>
              {businessOrders.map((order) => (
                <tr key={order.id} className="border-b border-gray-700">
                  <td className="p-4">{order.id}</td>
                  <td className="p-4">{order["37.5Kg"]}</td>
                  <td className="p-4">{order["12.5Kg"]}</td>
                  <td className="p-4">{order["5Kg"]}</td>
                  <td className="p-4">{order["2.5Kg"]}</td>
                  <td className="p-4">{order.totalPrice}</td>
                  <td className="p-4">{order.orderDate}</td>
                  <td className="p-4 flex space-x-2">
                    <Button
                      className="bg-green-500 px-2 py-1 rounded"
                      onClick={() => {
                        setBusinessOrders((prevOrders) =>
                          prevOrders.map((ord) =>
                            ord.id === order.id ? { ...ord, status: "Approved" } : ord
                          )
                        );
                      }}
                    >
                      Approve
                    </Button>
                    <Button
                      className="bg-red-500 px-2 py-1 rounded"
                      onClick={() => {
                        setBusinessOrders((prevOrders) =>
                          prevOrders.map((ord) =>
                            ord.id === order.id ? { ...ord, status: "Rejected" } : ord
                          )
                        );
                      }}
                    >
                      Reject
                    </Button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

                {/* Approved Business Gas Orders */}
                <div className="mb-8">
          <h2 className="text-xl font-semibold mb-4">Approved Business Gas Orders</h2>
          <table className="w-full bg-gray-800 rounded-lg overflow-hidden">
            <thead>
              <tr className="bg-gray-700">
                <th className="p-4 text-left">Order ID</th>
                <th className="p-4 text-left">37.5Kg</th>
                <th className="p-4 text-left">12.5Kg</th>
                <th className="p-4 text-left">5Kg</th>
                <th className="p-4 text-left">2.5Kg</th>
                <th className="p-4 text-left">Total Price</th>
                <th className="p-4 text-left">Order Date</th>
                <th className="p-4 text-left">Delivery Date</th>
                <th className="p-4 text-left">Remaining Days</th>
                <th className="p-4 text-left">Status</th>
              </tr>
            </thead>
            <tbody>
              {approvedBusinessOrders.map((order) => (
                <tr key={order.id} className="border-b border-gray-700">
                  <td className="p-4">{order.id}</td>
                  <td className="p-4">{order["37.5Kg"]}</td>
                  <td className="p-4">{order["12.5Kg"]}</td>
                  <td className="p-4">{order["5Kg"]}</td>
                  <td className="p-4">{order["2.5Kg"]}</td>
                  <td className="p-4">{order.totalPrice}</td>
                  <td className="p-4">{order.orderDate}</td>
                  <td className="p-4">{order.deliveryDate}</td>
                  <td className="p-4">{order.remainingDays} days</td>
                  <td className="p-4">{order.status}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        {/* Outlet Details Area */}
        <div className="mb-8">
          <h2 className="text-xl font-semibold mb-4">Outlet Details</h2>
          <div className="flex items-center space-x-4 mb-4">
            <select
              className="p-2 bg-gray-700 text-white rounded"
              value={selectedDistrict}
              onChange={(e) => setSelectedDistrict(e.target.value)}
            >
              <option value="">Select District</option>
              <option value="District A">District A</option>
              <option value="District B">District B</option>
            </select>
            <select
              className="p-2 bg-gray-700 text-white rounded"
              value={selectedOutletId}
              onChange={(e) => setSelectedOutletId(parseInt(e.target.value))}
            >
              <option value="">Select Outlet</option>
              {outlets.map((outlet) => (
                <option key={outlet.id} value={outlet.id}>
                  {outlet.name}
                </option>
              ))}
            </select>
            <Button
              className="bg-blue-500 px-4 py-2 rounded"
              onClick={() => {}}
            >
              View
            </Button>
          </div>
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
            {selectedOutletId &&
              outlets
                .filter((outlet) => outlet.id === selectedOutletId)
                .map((outlet) => (
                  <>
                    <div
                      key={`${outlet.id}-375`}
                      className="bg-gray-800 p-6 rounded-lg shadow-lg text-center"
                    >
                      <h3 className="text-lg font-bold">37.5Kg</h3>
                      <p className="text-2xl font-bold text-blue-500">
                        {outlet.stock["37.5Kg"]}
                      </p>
                    </div>
                    <div
                      key={`${outlet.id}-125`}
                      className="bg-gray-800 p-6 rounded-lg shadow-lg text-center"
                    >
                      <h3 className="text-lg font-bold">12.5Kg</h3>
                      <p className="text-2xl font-bold text-blue-500">
                        {outlet.stock["12.5Kg"]}
                      </p>
                    </div>
                    <div
                      key={`${outlet.id}-5`}
                      className="bg-gray-800 p-6 rounded-lg shadow-lg text-center"
                    >
                      <h3 className="text-lg font-bold">5Kg</h3>
                      <p className="text-2xl font-bold text-blue-500">
                        {outlet.stock["5Kg"]}
                      </p>
                    </div>
                    <div
                      key={`${outlet.id}-25`}
                      className="bg-gray-800 p-6 rounded-lg shadow-lg text-center"
                    >
                      <h3 className="text-lg font-bold">2.5Kg</h3>
                      <p className="text-2xl font-bold text-blue-500">
                        {outlet.stock["2.5Kg"]}
                      </p>
                    </div>
                  </>
                ))}
          </div>
        </div>

        {/* Outlet Bulk Orders Table */}
        <div className="mb-8">
          <h2 className="text-xl font-semibold mb-4">Outlet Bulk Orders</h2>
          <table className="w-full bg-gray-800 rounded-lg overflow-hidden">
            <thead>
              <tr className="bg-gray-700">
                <th className="p-4 text-left">Order ID</th>
                <th className="p-4 text-left">37.5Kg</th>
                <th className="p-4 text-left">12.5Kg</th>
                <th className="p-4 text-left">5Kg</th>
                <th className="p-4 text-left">2.5Kg</th>
                <th className="p-4 text-left">Order Date</th>
                <th className="p-4 text-left">Delivery Date</th>
                <th className="p-4 text-left">Remaining Days</th>
                <th className="p-4 text-left">Status</th>
              </tr>
            </thead>
            <tbody>
              {bulkOrders.map((order) => (
                <tr key={order.id} className="border-b border-gray-700">
                  <td className="p-4">{order.id}</td>
                  <td className="p-4">{order["37.5Kg"]}</td>
                  <td className="p-4">{order["12.5Kg"]}</td>
                  <td className="p-4">{order["5Kg"]}</td>
                  <td className="p-4">{order["2.5Kg"]}</td>
                  <td className="p-4">{order.orderDate}</td>
                  <td className="p-4">{order.deliveryDate}</td>
                  <td className="p-4">{order.remainingDays} days</td>
                  <td className="p-4">{order.status}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </motion.div>

      {/* Order Details Popup */}
      {isOrderDetailsPopupOpen && (
        <div className="fixed inset-0 bg-black bg-opacity-75 flex justify-center items-center z-50">
          <div className="bg-gray-800 p-8 rounded-lg w-full max-w-md">
            <h2 className="text-xl font-semibold mb-4">Order Details</h2>
            <div className="space-y-4">
              <p>
                <span className="font-bold">Order ID:</span> {selectedOrder?.id}
              </p>
              <p>
                <span className="font-bold">District:</span>{" "}
                {selectedOrder?.district}
              </p>
              <p>
                <span className="font-bold">Outlet Name:</span>{" "}
                {selectedOrder?.outletName}
              </p>
              <p>
                <span className="font-bold">37.5Kg:</span>{" "}
                {selectedOrder?.["37.5Kg"]}
              </p>
              <p>
                <span className="font-bold">12.5Kg:</span>{" "}
                {selectedOrder?.["12.5Kg"]}
              </p>
              <p>
                <span className="font-bold">5Kg:</span> {selectedOrder?.["5Kg"]}
              </p>
              <p>
                <span className="font-bold">2.5Kg:</span>{" "}
                {selectedOrder?.["2.5Kg"]}
              </p>
              <p>
                <span className="font-bold">Order Date:</span>{" "}
                {selectedOrder?.orderDate}
              </p>
              <label className="block">
                Select Delivery Date:
                <input
                  type="date"
                  className="p-2 mt-2 bg-gray-700 text-white rounded w-full"
                  value={deliveryDate}
                  onChange={(e) => setDeliveryDate(e.target.value)}
                />
              </label>
            </div>
            <div className="flex space-x-4 mt-6">
              <Button
                className="bg-green-500 px-4 py-2 rounded"
                onClick={() => handleOrderApproval(selectedOrder?.id, "Approved")}
              >
                Approve
              </Button>
              <Button
                className="bg-red-500 px-4 py-2 rounded"
                onClick={() => handleOrderApproval(selectedOrder?.id, "Rejected")}
              >
                Reject
              </Button>
              <button
                className="text-blue-500 hover:text-blue-400"
                onClick={() => setIsOrderDetailsPopupOpen(false)}
              >
                Close
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default HeadOfficeDashboard;
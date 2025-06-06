// src/pages/OutletManagerDashboard.jsx
import { useState, useEffect } from "react";
import Navbar from "../components/ClientNav";
import Button from "../components/Button";
import { motion } from "framer-motion";
import { useParams } from "react-router-dom";
import ManagerService from '../services/ManagerService';

const OutletManagerDashboard = () => {

    const { email } = useParams();
    const [outlet, setOutlet] = useState([]);
    const [schedule, setSchedule] = useState([]);
    const [requests, setRequests] = useState([]);

    const loadOutlet = async () => {
        try {
            let res = await ManagerService.fetchOutletByManager(email);
            if (res.status === 200) {
                setOutlet(res.data.data);
                console.log("Outlet : ", res.data.data);
            } else {
                console.log("Fetching error: " + res);
            }
        } catch (error) {
            console.log("Error fetching reuests:", error);
        }
    };

    const loadRequestsList = async () => {
        try {
            let res = await ManagerService.fetchClientRequestsByOutlet(outlet.outletId);
            if (res.status === 200) {
                setRequests(res.data.data);
                console.log("Requests : ", res.data.data);
            } else {
                console.log("Fetching error: " + res);
            }
        } catch (error) {
            console.log("Error fetching reuests:", error);
        }
    };

    const loadScheduleList = async () => {
        try {
            let res = await ManagerService.fetchShedulesByOutlet(outlet.outletId);
            if (res.status === 200) {
                setSchedule(res.data.data);
                console.log("Schedules : ", res.data.data);
            } else {
                console.log("Fetching error: " + res);
            }
        } catch (error) {
            console.log("Error fetching reuests:", error);
        }
    };


    useEffect(() => {
        loadOutlet();
    }, [email]);

    useEffect(() => {
        if (outlet?.outletId) {
            loadRequestsList();
            loadScheduleList();
        }
    }, [outlet]);

    const calculateRemainingDays = (deliveryDate) => {
        const currentDate = new Date();
        const delivery = new Date(deliveryDate);
        const timeDifference = delivery - currentDate;
        const daysRemaining = Math.ceil(timeDifference / (1000 * 3600 * 24)); // Convert milliseconds to days
        return daysRemaining >= 0 ? daysRemaining : 0; // Avoid negative days
    };


    // Dummy Data for Stock Levels
    const stockLevels = [
        { id: 1, name: "37.5Kg", stock: 50, price: "Rs. 10,000" },
        { id: 2, name: "12.5Kg", stock: 120, price: "Rs. 4,000" },
        { id: 3, name: "5Kg", stock: 80, price: "Rs. 2,000" },
        { id: 4, name: "2.5Kg", stock: 200, price: "Rs. 1,000" },

    ];



    // Dummy Data for Pending Orders Table (Reallocation Popup)
    const [pendingOrders, setPendingOrders] = useState([
        {
            id: 1,
            customerId: "2",
            gasType: "12.5Kg",
            price: "3950",
            requestDate: "2025-03-12T16:57:58.000+00:00",
            status: "PENDING",
        },
    ]);


    // State for Reallocation Popup
    const [isReallocationPopupOpen, setIsReallocationPopupOpen] = useState(false);

    // State for Bulk Order Popup
    const [isBulkOrderPopupOpen, setIsBulkOrderPopupOpen] = useState(false);
    const [bulkOrderForm, setBulkOrderForm] = useState({
        "37.5Kg": 0,
        "12.5Kg": 0,
        "5Kg": 0,
        "2.5Kg": 0,
    });

    // Handle Reallocation Approval
    const handleReallocationApproval = (id) => {
        setPendingOrders((prevOrders) =>
            prevOrders.map((order) =>
                order.id === id ? { ...order, status: "Approved" } : order
            )
        );
    };

    // Handle Bulk Order Submission
    const handleBulkOrderSubmit = () => {
        alert("Bulk order submitted successfully!");
        const newOrder = {
            id: bulkOrders.length + 1,
            ...bulkOrderForm,
            orderDate: "2023-10-05",
            deliveryDate: "2023-10-15",
            remainingDays: 10,
            status: "Pending",
        };
        setBulkOrders([...bulkOrders, newOrder]);
        setBulkOrderForm({ "37.5Kg": 0, "12.5Kg": 0, "5Kg": 0, "2.5Kg": 0 });
        setIsBulkOrderPopupOpen(false);
        alert("Bulk order submitted successfully!");
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
                <h1 className="text-3xl font-bold mb-8">{outlet.outletName}</h1>

                {/* Stock Levels */}
                <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-5 gap-4 mb-8">
                    {stockLevels.map((item) => (
                        <div
                            key={item.id}
                            className="bg-gray-800 p-6 rounded-lg shadow-lg text-center"
                        >
                            <h3 className="text-lg font-bold">{item.name}</h3>
                            <p className="text-2xl font-bold text-blue-500">{item.stock}</p>
                            <p className="text-gray-400">Price: {item.price}</p>
                        </div>
                    ))}
                    <div className="bg-gray-800 p-6 rounded-lg shadow-lg text-center">

                        <Button
                            className="bg-blue-500 px-4 py-7 rounded"
                            onClick={() => setIsBulkOrderPopupOpen(true)}
                        >
                            Create Delivery Schedule
                        </Button>

                    </div>
                </div>

                {/* Approved Orders Table */}
                <div className="mb-8">
                    <h2 className="text-xl font-semibold mb-4">Approved Orders</h2>
                    <table className="w-full bg-gray-800 rounded-lg overflow-hidden">
                        <thead>
                            <tr className="bg-gray-700">
                                <th className="p-4 text-left">Order ID</th>
                                <th className="p-4 text-left">Customer ID</th>
                                <th className="p-4 text-left">Gas Type</th>
                                <th className="p-4 text-left">Price</th>
                                <th className="p-4 text-left">Request Date</th>
                                <th className="p-4 text-left">Status</th>
                                <th className="p-4 text-left">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {requests.map((order) => (
                                <tr key={order.id} className="border-b border-gray-700">
                                    <td className="p-4">{order.requestId}</td>
                                    <td className="p-4">{order.clientId}</td>
                                    <td className="p-4">{order.resDetails[0].gasType}</td>
                                    <td className="p-4">{order.resDetails[0].gasPrice}</td>
                                    <td className="p-4">{order.requestDate}</td>
                                    <td className="p-4">{order.status}</td>
                                    <td className="p-4">
                                        <button
                                            className="text-blue-500 hover:underline"
                                            onClick={() => setIsReallocationPopupOpen(true)}
                                        >
                                            Reallocate
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>

                {/* Pending Orders Table */}
                <div className="mb-8">
                    <h2 className="text-xl font-semibold mb-4">Pending Orders</h2>
                    <table className="w-full bg-gray-800 rounded-lg overflow-hidden">
                        <thead>
                            <tr className="bg-gray-700">
                                <th className="p-4 text-left">Order ID</th>
                                <th className="p-4 text-left">Customer ID</th>
                                <th className="p-4 text-left">Gas Type</th>
                                <th className="p-4 text-left">Price</th>
                                <th className="p-4 text-left">Request Date</th>
                                <th className="p-4 text-left">Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            {pendingOrders.map((order) => (
                                <tr key={order.id} className="border-b border-gray-700">
                                    <td className="p-4">{order.id}</td>
                                    <td className="p-4">{order.customerId}</td>
                                    <td className="p-4">{order.gasType}</td>
                                    <td className="p-4">{order.price}</td>
                                    <td className="p-4">{order.requestDate}</td>
                                    <td className="p-4">{order.status}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>

                {/* Bulk Order Request Button */}


                {/* Bulk Order Request Table */}
                <div className="mb-8">
                    <h2 className="text-xl font-semibold mb-4">Delivery Schedules</h2>
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
                            {schedule.map((order) => (
                                <tr key={order.scheduleId} className="border-b border-gray-700">
                                    <td className="p-4">{order.scheduleId}</td>
                                    <td className="p-4">
                                        {order.stockList.find((stock) => stock.gasType === "37.5kg")?.quantity || 0}
                                    </td>
                                    <td className="p-4">
                                        {order.stockList.find((stock) => stock.gasType === "12.5kg")?.quantity || 0}
                                    </td>
                                    <td className="p-4">
                                        {order.stockList.find((stock) => stock.gasType === "5kg")?.quantity || 0}
                                    </td>
                                    <td className="p-4">
                                        {order.stockList.find((stock) => stock.gasType === "2.5kg")?.quantity || 0}
                                    </td>
                                    <td className="p-4">{order.deliveryDate}</td>
                                    <td className="p-4">{order.deliveryDate}</td>
                                    <td className="p-4">
                                        {calculateRemainingDays(order.deliveryDate)} days
                                    </td>
                                    <td className="p-4">{order.status}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>

                </div>
            </motion.div>

            {/* Reallocation Popup */}
            {isReallocationPopupOpen && (
                <div className="fixed inset-0 bg-black bg-opacity-75 flex justify-center items-center z-50">
                    <div className="bg-gray-800 p-8 rounded-lg">
                        <h2 className="text-xl font-semibold mb-4">Reallocate Gas</h2>
                        <table className="w-full bg-gray-800 rounded-lg overflow-hidden">
                            <thead>
                                <tr className="bg-gray-700">
                                    <th className="p-4 text-left">Order ID</th>
                                    <th className="p-4 text-left">Customer ID</th>
                                    <th className="p-4 text-left">Gas Type</th>
                                    <th className="p-4 text-left">Price</th>
                                    <th className="p-4 text-left">Request Date</th>
                                    <th className="p-4 text-left">Status</th>
                                    <th className="p-4 text-left">Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {pendingOrders.map((order) => (
                                    <tr key={order.id} className="border-b border-gray-700">
                                        <td className="p-4">{order.id}</td>
                                        <td className="p-4">{order.customerId}</td>
                                        <td className="p-4">{order.gasType}</td>
                                        <td className="p-4">{order.price}</td>
                                        <td className="p-4">{order.requestDate}</td>
                                        <td className="p-4">{order.status}</td>
                                        <td className="p-4">
                                            <button
                                                className="text-green-500 hover:underline"
                                                onClick={() => handleReallocationApproval(order.id)}
                                            >
                                                Approve
                                            </button>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                        <button
                            className="mt-4 text-blue-500 hover:text-blue-400"
                            onClick={() => setIsReallocationPopupOpen(false)}
                        >
                            Close
                        </button>
                    </div>
                </div>
            )}

            {/* Bulk Order Popup */}
            {isBulkOrderPopupOpen && (
                <div className="fixed inset-0 bg-black bg-opacity-75 flex justify-center items-center z-50">
                    <div className="bg-gray-800 p-8 rounded-lg w-full max-w-md">
                        <h2 className="text-xl font-semibold mb-4">Create Bulk Order</h2>
                        <div className="grid grid-cols-2 gap-4 mb-4">
                            {Object.keys(bulkOrderForm).map((type) => (
                                <div key={type} className="flex flex-col space-y-2">
                                    <label className="text-gray-300">{type}</label>
                                    <input
                                        type="number"
                                        min="0"
                                        value={bulkOrderForm[type]}
                                        onChange={(e) =>
                                            setBulkOrderForm({
                                                ...bulkOrderForm,
                                                [type]: parseInt(e.target.value || 0),
                                            })
                                        }
                                        className="p-2 bg-gray-700 text-white rounded w-full"
                                    />
                                </div>
                            ))}
                        </div>
                        <Button
                            className="bg-blue-500 px-4 py-2 rounded"
                            onClick={handleBulkOrderSubmit}
                        >
                            Submit Request
                        </Button>
                        <button
                            className="mt-4 px-20 text-blue-500 hover:text-blue-400 "
                            onClick={() => setIsBulkOrderPopupOpen(false)}
                        >
                            Close
                        </button>
                    </div>
                </div>
            )}
            {/* </motion.div> */}
        </div >
    );
};

export default OutletManagerDashboard;
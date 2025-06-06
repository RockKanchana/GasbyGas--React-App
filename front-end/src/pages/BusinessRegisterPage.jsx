// src/pages/BusinessRegisterPage.jsx
import { useState } from "react";
import Navbar from "../components/Navbar";
import InputField from "../components/InputField";
import Button from "../components/Button";
import { motion } from "framer-motion";
import { Link } from "react-router-dom";

const BusinessRegisterPage = () => {
  const [formData, setFormData] = useState({
    businessName: "",
    businessRegistrationNumber: "",
    phone: "",
    email: "",
    address: "",
    certification: null,
    password: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleFileChange = (e) => {
    setFormData({ ...formData, certification: e.target.files[0] });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Simulate business registration logic
    alert(`Business registered successfully: ${formData.businessName}`);
  };

  return (
    <div className="min-h-screen bg-gray-900 text-white">
      <Navbar />
      <motion.div
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ duration: 1 }}
        className="flex items-center justify-center h-screen"
      >
        <form
          onSubmit={handleSubmit}
          className="bg-gray-800 p-8 rounded-lg shadow-lg w-full max-w-md space-y-4"
        >
          <h2 className="text-2xl font-bold mb-4">Business Registration</h2>

          {/* Business Fields */}
          <InputField
            label="Business Name"
            type="text"
            placeholder="Enter business name"
            name="businessName"
            value={formData.businessName}
            onChange={handleChange}
          />
          <InputField
            label="Business Registration Number"
            type="text"
            placeholder="Enter registration number"
            name="businessRegistrationNumber"
            value={formData.businessRegistrationNumber}
            onChange={handleChange}
          />
          <InputField
            label="Phone Number"
            type="text"
            placeholder="Enter phone number"
            name="phone"
            value={formData.phone}
            onChange={handleChange}
          />
          <InputField
            label="Email"
            type="email"
            placeholder="Enter email"
            name="email"
            value={formData.email}
            onChange={handleChange}
          />
          <InputField
            label="Address"
            type="text"
            placeholder="Enter address"
            name="address"
            value={formData.address}
            onChange={handleChange}
          />
          <div className="mb-4">
            <label className="block text-gray-300">Upload Certification</label>
            <input
              type="file"
              accept=".pdf,.jpg,.png"
              onChange={handleFileChange}
              className="w-full px-4 py-2 bg-gray-700 text-white rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>
          <InputField
            label="Password"
            type="password"
            placeholder="Enter password"
            name="password"
            value={formData.password}
            onChange={handleChange}
          />

          {/* Submit Button */}
          <Button className="bg-blue-500 w-full mt-4" type="submit">
            Register
          </Button>

          {/* Navigation to Customer Registration */}
          <p className="text-center text-gray-400">
            Registering as a customer?{" "}
            <Link to="/register" className="text-blue-500 hover:underline">
              Click here
            </Link>
          </p>
        </form>
      </motion.div>
    </div>
  );
};

export default BusinessRegisterPage;
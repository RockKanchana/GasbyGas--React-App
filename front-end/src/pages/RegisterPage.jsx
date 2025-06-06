// src/pages/RegisterPage.jsx
import { useState } from "react";
import Navbar from "../components/Navbar";
import InputField from "../components/InputField";
import Button from "../components/Button";
import { motion } from "framer-motion";
import { Link, useNavigate } from 'react-router-dom';
import AuthService from '../services/AuthService';

const RegisterPage = () => {
  const [formData, setFormData] = useState({
    name: "",
    address: "",
    phone: "",
    email: "",
    nic: "",
    password: "",
    registrationNumber: "",
    certification: null,
  });

  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const data = {
        name: formData.name,
        email: formData.email,
        password: formData.password,
        address: formData.address,
        contact: formData.phone,
        roleId: 3,
        nic: formData.nic,
        registrationNumber: formData.registrationNumber,
        certification: formData.certification,
      };

      const resp = await AuthService.postSign(data);

      if (resp && resp.data) {
        if (resp.data.code === 500) {
          alert(resp.data.message);
        } else {
          console.log("Sign successful", resp.data.message);
          navigate(`/otp/${formData.email}`);
        }
      } else {
        console.error("Unexpected response structure:", resp);
        alert("Registration failed. Please try again.");
      }
    } catch (error) {
      console.error("Error occurred:", error);
      alert("An error occurred during registration.");
    }
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
          <h2 className="text-2xl font-bold mb-4">Customer Registration</h2>

          {/* Customer Fields */}
          <InputField
            label="Name"
            type="text"
            placeholder="Enter your name"
            name="name"
            value={formData.name}
            onChange={handleChange}
          />
          <InputField
            label="Address"
            type="text"
            placeholder="Enter your address"
            name="address"
            value={formData.address}
            onChange={handleChange}
          />
          <InputField
            label="Phone Number"
            type="text"
            placeholder="Enter your phone number"
            name="phone"
            value={formData.phone}
            onChange={handleChange}
          />
          <InputField
            label="Email"
            type="email"
            placeholder="Enter your email"
            name="email"
            value={formData.email}
            onChange={handleChange}
          />
          <InputField
            label="NIC"
            type="text"
            placeholder="Enter your NIC"
            name="nic"
            value={formData.nic}
            onChange={handleChange}
          />
          <InputField
            label="Password"
            type="password"
            placeholder="Enter your password"
            name="password"
            value={formData.password}
            onChange={handleChange}
          />

          {/* Submit Button */}
          <Button className="bg-blue-500 w-full mt-4" type="submit">
            Register
          </Button>

          {/* Navigation to Business Registration */}
          <p className="text-center text-gray-400">
            Registering for a business?{" "}
            <Link to="/business-register" className="text-blue-500 hover:underline">
              Click here
            </Link>
          </p>
        </form>
      </motion.div>
    </div>
  );
};

export default RegisterPage;
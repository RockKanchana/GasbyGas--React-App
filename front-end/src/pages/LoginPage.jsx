// src/pages/LoginPage.jsx
import { useState } from "react";
import Navbar from "../components/Navbar";
import InputField from "../components/InputField";
import Button from "../components/Button";
import { motion } from "framer-motion";
import { Link } from "react-router-dom";
import AuthService from '../services/AuthService';
import { useNavigate } from "react-router-dom";

const LoginPage = () => {

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleEmailChange = (event) => {
    setEmail(event.target.value);
  };
  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };


  const handleSubmit = async () => {
    const data = {
      email: email,
      password: password
    };
    try {
      const resp = await AuthService.postLogin(data);

      if (resp.status === 200) {
        localStorage.setItem("token", resp.data.data.token);
        const role = resp.data.data.role;

        switch (role) {
          case 1:
            navigate(`/head-office-dashboard/${email}`);
            break;
          case 2:
            navigate(`/outlet-manager-dashboard/${email}`);
            break;
          case 3:
            navigate(`/customer-dashboard/${email}`);
            break;
          case 4:
            navigate(`/customer-dashboard/${email}`);
            break;
          default:
            console.log("Unknown role");
        }
      } else {
        console.log("Login failed", resp);
      }
    } catch (error) {
      console.error("Error occurred:", error);
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
        {/* 3D Card Design */}
        <motion.div
          className="relative bg-gray-800 p-8 rounded-2xl shadow-lg w-full max-w-md border-2 border-transparent"
          whileInView={{
            borderColor: ["transparent", "#4b5563", "#6b7280", "transparent"],
          }}
          transition={{
            duration: 3,
            repeat: Infinity,
            ease: "linear",
          }}
        >
          {/* Shadows and Glow Effect */}
          <div className="absolute inset-0 bg-gradient-to-br from-blue-500 to-gray-950 rounded-2xl blur-md opacity-20"></div>

          {/* Content */}
          <div className="relative z-10">
            <h2 className="text-2xl font-bold mb-6 text-center">Login</h2>
            <InputField
              label="Email"
              type="email"
              placeholder="Enter your email"
              name="email"
              value={email}
              onChange={handleEmailChange}
            />
            <InputField
              label="Password"
              type="password"
              placeholder="Enter your password"
              name="password"
              value={password}
              onChange={handlePasswordChange}
            />
            <div className="flex justify-between items-center mt-4">
              <Link to="/forgot-password" className="text-blue-500 hover:underline">
                Forgot Password?
              </Link>
            </div>
            <Button
              className="bg-blue-500 w-full mt-6 text-lg font-bold"
              type="submit"
              onClick={handleSubmit}
            >
              Login
            </Button>
            <p className="text-center text-gray-400 mt-4">
              Don't have an account?{" "}
              <Link to="/register" className="text-blue-500 hover:underline">
                Register here
              </Link>
            </p>
          </div>
        </motion.div>
      </motion.div>
    </div>
  );
};

export default LoginPage;
// src/components/Navbar.jsx
import { motion } from "framer-motion";
import { Link } from "react-router-dom";

const Navbar = () => {
  return (
    <nav className="bg-gray-800 text-white py-4 px-8 shadow-md">
      <div className="flex justify-between items-center">
        {/* Logo and Brand Name */}
        <Link to="/" className="flex items-center space-x-2">
          <img
            src="/logoLight.png"
            alt="GasByGas Logo"
            className="w-8 rounded-full"
          />
          <h1 className="text-3xl font-bold">GasByGas</h1>
        </Link>

        {/* Buttons */}
        <div className="space-x-4">
          {/* Login Button */}
          <motion.div
            whileHover={{ scale: 1.1 }}
            whileTap={{ scale: 0.9 }}
            className="inline-block"
          >
            <Link to="/login">
              <button className="bg-blue-500 px-4 py-2 rounded-lg">Login</button>
            </Link>
          </motion.div>

          {/* Register Button */}
          <motion.div
            whileHover={{ scale: 1.1 }}
            whileTap={{ scale: 0.9 }}
            className="inline-block"
          >
            <Link to="/register">
              <button className="bg-orange-500 px-4 py-2 rounded-lg">Register</button>
            </Link>
          </motion.div>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
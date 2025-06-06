// src/pages/HomePage.jsx
import Navbar from "../components/Navbar";
import { motion } from "framer-motion";
import { Link } from 'react-router-dom';

const HomePage = () => {
  return (
    <div className="min-h-screen bg-gray-900 text-white">
      <Navbar />
      <motion.div
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ duration: 1 }}
        className="flex flex-col items-center justify-center h-screen space-y-8"
      >
        <h1 className="text-4xl font-bold">Welcome to GasByGas</h1>
        <p className="text-gray-400">Efficient gas delivery at your fingertips.</p>
        <div className="space-x-4">
          <Link to={`/login`}>
            <motion.button
              whileHover={{ scale: 1.1 }}
              whileTap={{ scale: 0.9 }}
              className="bg-blue-500 px-6 py-2 rounded-lg"
            >
              Login
            </motion.button>
          </Link>
          <Link to={`/register`}>
            <motion.button
              whileHover={{ scale: 1.1 }}
              whileTap={{ scale: 0.9 }}
              className="bg-orange-500 px-6 py-2 rounded-lg"
            >
              Register
            </motion.button>
          </Link>
        </div>
      </motion.div>
    </div>
  );
};

export default HomePage;
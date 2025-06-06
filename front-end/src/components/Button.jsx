// src/components/Button.jsx
import { motion } from "framer-motion";

const Button = ({ children, onClick, className }) => {
  return (
    <motion.button
      className={`px-6 py-3 rounded-lg font-semibold ${className}`}
      onClick={onClick}
    >
      {children}
    </motion.button>
  );
};

export default Button;
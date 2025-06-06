import { motion } from "framer-motion";
import { Link, useNavigate } from "react-router-dom";
import { useParams } from "react-router-dom";

const ClientNav = () => {
    const { email } = useParams();
    const navigate = useNavigate();

    const handleLogout = () => {
        console.log("Logout clicked!");
        localStorage.removeItem("token");
        navigate("/");
    };


    return (
        <nav className="bg-gray-700 text-white py-5 px-8"> {/* Increased navbar height */}
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
                <div className="flex flex-col items-center space-y-2"> {/* Center email and place below logout */}
                    <motion.div
                        whileHover={{ scale: 1.1 }}
                        whileTap={{ scale: 0.9 }}
                        className="inline-block"
                    >
                        <button className="bg-red-500 px-4 py-2 rounded-lg" onClick={handleLogout}>Log Out</button>
                    </motion.div>

                    <motion.div
                        whileHover={{ scale: 1.1 }}
                        whileTap={{ scale: 0.9 }}
                        className="inline-block text-center"
                    >
                        <label className="text-gray-300 text-sm">{email}</label>
                    </motion.div>
                </div>
            </div>
        </nav>
    );
};

export default ClientNav;

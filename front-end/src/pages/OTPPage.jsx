import { useState } from "react";
import { motion } from "framer-motion";
import Button from "../components/Button";
import InputField from "../components/InputField";
import Navbar from "../components/Navbar";
import AuthService from '../services/AuthService';
import { useParams } from "react-router-dom";

const OTPPage = () => {
    const { email } = useParams();
    const [otpData, setOtpData] = useState({ emailOtp: "", contactOtp: "" });
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(null);
  
    const handleChange = (e) => {
      setOtpData({ ...otpData, [e.target.name]: e.target.value });
    };
  
    const handleSubmit = async (e) => {
      e.preventDefault();
      setError(null);
      setSuccess(null);
      try {
        const response = await AuthService.verifyOTP({
          email,
          emailOtp: otpData.emailOtp,
          contactOtp: otpData.contactOtp,
        });
        if (response.status === 200) {
          setSuccess("OTP verified successfully!");
        } else {
          setError("Invalid OTP. Please try again.");
        }
      } catch (err) {
        setError("An error occurred. Please try again.");
      }
    };
  
    return (
      <div className="min-h-screen bg-gray-900 text-white">
        <Navbar />
        <div className="flex items-center justify-center min-h-screen">
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            transition={{ duration: 1 }}
            className="bg-gray-800 p-8 rounded-lg shadow-lg w-full max-w-md"
          >
            <h2 className="text-2xl font-bold mb-4 text-center">Verify OTP</h2>
            {error && <p className="text-red-500 text-center">{error}</p>}
            {success && <p className="text-green-500 text-center">{success}</p>}
            <form onSubmit={handleSubmit} className="space-y-4">
              <InputField
                label="Email"
                type="email"
                placeholder="Enter your email"
                name="email"
                value={email}
                disabled
              />
              <InputField
                label="Email OTP"
                type="text"
                placeholder="Enter Email OTP"
                name="emailOtp"
                value={otpData.emailOtp}
                onChange={handleChange}
              />
              <InputField
                label="Contact OTP"
                type="text"
                placeholder="Enter Contact OTP"
                name="contactOtp"
                value={otpData.contactOtp}
                onChange={handleChange}
              />
              <Button className="bg-blue-500 w-full mt-4" type="submit">
                Submit OTP
              </Button>
            </form>
          </motion.div>
        </div>
      </div>
    );
  };
  
  export default OTPPage;
  
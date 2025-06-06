// src/App.jsx
import { Routes, Route } from "react-router-dom";
import HomePage from "./pages/HomePage";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import BusinessRegisterPage from "./pages/BusinessRegisterPage";
import CustomerDashboard from "./pages/CustomerDashboard";
import OutletManagerDashboard from "./pages/OutletManagerDashboard";
import HeadOfficeDashboard from "./pages/HeadOfficeDashboard";
import OTPPage from "./pages/OTPPage";

function App() {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />
      <Route path="/otp/:email" element={<OTPPage />} />
      <Route path="/business-register" element={<BusinessRegisterPage />} />
      <Route path="/customer-dashboard/:email" element={<CustomerDashboard />} />
      <Route path="/outlet-manager-dashboard/:email" element={<OutletManagerDashboard />} />
      <Route path="/head-office-dashboard/:email" element={<HeadOfficeDashboard />} />
    </Routes>
  );
}

export default App;
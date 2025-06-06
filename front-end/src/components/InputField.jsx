// src/components/InputField.jsx
import React from "react";

const InputField = ({ label, type, placeholder, name, value, onChange }) => {
  return (
    <div className="mb-4">
      {label && <label className="block text-gray-300">{label}</label>}
      <input
        type={type || "text"}
        placeholder={placeholder}
        name={name}
        value={value}
        onChange={onChange}
        className="w-full px-4 py-2 bg-gray-700 text-white rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
      />
    </div>
  );
};

export default InputField;
import { createContext } from "react";
import { useState } from "react";
import axios from 'axios';
import { toast } from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';

export const DoctorContext = createContext();

const DoctorContextProvider = (props) => {

  const backendUrl = import.meta.env.VITE_BACKEND_URL;
  const [dToken, setDToken] = useState("");
  const [appointments, setAppointments] = useState([]);

  const [dashData, setDashData] = useState(false);

  const [profileData, setProfileData] = useState(false);

  const getAppointments = async () => {
    try {
      const { data } = await axios.get(backendUrl + '/api/doctor/appointments', { headers: { dtoken: dToken } })
      if (data.success) {
        const mappedAppointments = data.appointments.map(app => ({ ...app, _id: app.id.toString() }));
        setAppointments(mappedAppointments)
      } else {
        toast.error(data.message)
      }
    } catch (error) {
      toast.error(error.message);
    }
  };

  const completeAppointment = async (appointmentId) => {
    try {
      const { data } = await axios.post(backendUrl + '/api/doctor/complete-appointment', { appointmentId }, { headers: { dtoken: dToken } })
      if (data.success) {
        toast.success(data.message || "Appointment completed")
        getAppointments()
      } else {
        toast.error(data.message)
      }
    } catch (error) {
      toast.error(error.message);
    }
  };

  const cancelAppointment = async (appointmentId) => {
    try {
      const { data } = await axios.post(backendUrl + '/api/doctor/cancel-appointment', { appointmentId }, { headers: { dtoken: dToken } })
      if (data.success) {
        toast.success(data.message || "Appointment cancelled")
        getAppointments()
      } else {
        toast.error(data.message)
      }
    } catch (error) {
      toast.error(error.message);
    }
  };

  const getDashData = async () => {
    try {
      const { data } = await axios.get(backendUrl + '/api/doctor/dashboard', { headers: { dtoken: dToken } })
      if (data.success) {
        const dashData = data.dashData;
        dashData.latestAppointments = dashData.latestAppointments.map(app => ({ ...app, _id: app.id.toString() }));
        setDashData(dashData)
      } else {
        toast.error(data.message)
      }
    } catch (error) {
      toast.error(error.message);
    }
  };

  const getProfileData = async () => {
    try {
      const { data } = await axios.get(backendUrl + '/api/doctor/profile', { headers: { dtoken: dToken } })
      if (data.success) {
        const profile = data.profileData;
        profile.address = {
            line1: profile.addressLine1 || "",
            line2: profile.addressLine2 || ""
        };
        setProfileData(profile)
      } else {
        toast.error(data.message)
      }
    }
    catch (error) {
      toast.error(error.message);
    }

  };

  const value = {
    dToken, setDToken,
    backendUrl,
    appointments, setAppointments,
    getAppointments,
    completeAppointment,
    cancelAppointment,
    dashData, setDashData,
    getDashData,
    profileData, setProfileData,
    getProfileData,
  };

  return (
    <DoctorContext.Provider value={value}>
      {props.children}
    </DoctorContext.Provider>
  );
};
export default DoctorContextProvider;


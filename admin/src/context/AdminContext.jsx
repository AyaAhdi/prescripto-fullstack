import { createContext } from "react";
import { useState } from "react";
import axios from 'axios';
import { toast } from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';


export const AdminContext = createContext();

const AdminContextProvider = (props) => {

  const [aToken, setAToken] = useState("")

  const [doctors, setDoctors] = useState([])

  const [appointments, setAppointments] = useState([])

  const [dashData, setDashData] = useState(false)

  const backendUrl = import.meta.env.VITE_BACKEND_URL

  const getAllDoctors = async () => {
    try {
      const { data } = await axios.get(backendUrl + '/api/doctor/list')
      if (data.success) {
        // Map id to _id for frontend compatibility
        const mappedDoctors = data.doctors.map(doc => ({ ...doc, _id: doc.id.toString() }));
        setDoctors(mappedDoctors)
      } else {
        toast.error(data.message)
      }
    } catch (error) {
      toast.error(error.message)
    }
  };

  const changeAvailability = async (docId) => {
    try {
      const { data } = await axios.post(backendUrl + '/api/doctor/change-availability', { docId }, { headers: { atoken: aToken } })
      if (data.success) {
        toast.success(data.message || "Availability changed")
        getAllDoctors()
      } else {
        toast.error(data.message)
      }
    } catch (error) {
      toast.error(error.message)
    }
  }

  const getAllAppointments = async () => {
    try {
      const { data } = await axios.get(backendUrl + '/api/admin/appointments', { headers: { atoken: aToken } })
      if (data.success) {
        const mappedAppointments = data.appointments.map(app => ({ ...app, _id: app.id.toString() }));
        setAppointments(mappedAppointments)
      } else {
        toast.error(data.message)
      }
    } catch (error) {
      toast.error(error.message)
    }
  }

  const cancelAppointment = async (appointmentId) => {
    try {
      const { data } = await axios.post(backendUrl + '/api/admin/cancel-appointment', { appointmentId }, { headers: { atoken: aToken } })
      if (data.success) {
        toast.success(data.message || "Appointment cancelled")
        getAllAppointments()
      } else {
        toast.error(data.message)
      }
    } catch (error) {
      toast.error(error.message)
    }
  }

  const getDashData = async () => {
    try {
      const { data } = await axios.get(backendUrl + '/api/admin/dashboard', { headers: { atoken: aToken } })
      if (data.success) {
        setDashData(data.dashData)
      } else {
        toast.error(data.message)
      }
    } catch (error) {
      toast.error(error.message)
    }
  }


  const value = {
    aToken, setAToken,
    backendUrl,
    doctors, getAllDoctors,
    changeAvailability,
    appointments, getAllAppointments,
    setAppointments, cancelAppointment,
    dashData, getDashData,
  };
  return (
    <AdminContext.Provider value={value}>
      {props.children}
    </AdminContext.Provider>
  );
};
export default AdminContextProvider;


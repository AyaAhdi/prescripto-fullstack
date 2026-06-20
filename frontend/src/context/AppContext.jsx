import { createContext, useEffect, useState } from "react";
import axios from 'axios';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

export const AppContext = createContext()

const AppContextProvider = (props) => {

  const currencySymbol = '$'

  const backendUrl = import.meta.env.VITE_BACKEND_URL

  const [doctors, setDoctors] = useState([])

  const [token, setToken] = useState(false)

  const [userData, setUserData] = useState(false)

  const getDoctorsData = async () => {
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
      console.log(error);
      toast.error(error.message);
    }
  }


  const loadUserProfileData = async () => {
    try {
      const { data } = await axios.get(backendUrl + '/api/user/get-profile', { headers: { token } })
      if (data.success) {
        const user = data.userData;
        user.address = {
            line1: user.addressLine1 || "",
            line2: user.addressLine2 || ""
        };
        setUserData(user)
      } else {
        toast.error(data.message)
      }
    } catch (error) {
      console.log(error);
      toast.error(error.message);
    }
  }

  const value = {
    doctors, getDoctorsData,
    currencySymbol,
    token,
    setToken,
    backendUrl,
    userData, setUserData,
    loadUserProfileData,
  }

  useEffect(() => {
    getDoctorsData()
  }, [])

  useEffect(() => {
    if (token) {
      loadUserProfileData()
    } else {
      setUserData(false)
    }
  }, [token])


  return (
    <AppContext.Provider value={value}>
      {props.children}
    </AppContext.Provider>
  )
}
export default AppContextProvider;




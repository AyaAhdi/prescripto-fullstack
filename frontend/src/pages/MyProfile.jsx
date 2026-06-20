import { useContext, useState } from 'react'
import { AppContext } from '../context/AppContext'
import { assets } from '../assets/assets'
import axios from 'axios'
import { toast } from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css';


const MyProfile = () => {

  const { userData, setUserData, token, backendUrl, loadUserProfileData } = useContext(AppContext)
  const [isEdit, setIsEdit] = useState(false)

  const [image, setImage] = useState(false)

  const updateUserProfileData = async () => {
    try {
      const performUpdate = async (imageBase64) => {
        const updateData = {
          id: userData.id,
          name: userData.name,
          phone: userData.phone,
          addressLine1: userData.address.line1,
          addressLine2: userData.address.line2,
          gender: userData.gender,
          dob: userData.dob,
          age: Number(userData.age),
          image: imageBase64 || userData.image
        };

        const { data } = await axios.post(backendUrl + '/api/user/update-profile', updateData, { headers: { token } })
        
        if (data.success) {
          toast.success("Profile updated successfully!")
          await loadUserProfileData()
          setIsEdit(false)
          setImage(false)
        } else {
          toast.error(data.message)
        }
      };

      if (image) {
        const reader = new FileReader();
        reader.readAsDataURL(image);
        reader.onloadend = () => {
          performUpdate(reader.result);
        };
      } else {
        performUpdate(null);
      }

    } catch (error) {
      console.log(error)
      toast.error(error.message)
    }
  }


  return userData && (

    <div className="max-w-lg flex flex-col gap-2 text-sm">

      <div className="flex items-center gap-4 mb-8 text-gray-500">
        {
          isEdit
            ? <>
                <label htmlFor='image'>
                  <img className='w-16 h-16 bg-gray-100 rounded-full cursor-pointer object-cover' src={image ? URL.createObjectURL(image) : (userData.image || assets.upload_area)} alt="" />
                </label>
                <input type="file" id="image" hidden onChange={(e) => setImage(e.target.files[0])} />
                <p>Upload profile <br /> picture</p>
              </>
            : <img className="w-16 h-16 rounded-full object-cover" src={userData.image} alt="" />
        }
      </div>


      {
        isEdit
          ? <input className="bg-gray-50 text-3xl font-medium max-w-60 mt-4" type="text" value={userData.name} onChange={e => setUserData(prev => ({ ...prev, name: e.target.value }))} />
          : <p className="font-medium text-3xl text-neutral-800 mt-4">{userData.name}</p>
      }

      <hr className="bg-zinc-400 h-px border-none" />
      <div>
        <p className="text-neutral-500 underline mt-3">CONTACT INFORMATION</p>
        <div className="grid grid-cols-[1fr_3fr] gap-y-2.5 mt-3 text-neutral-700">
          <p className='font-medium'> Email id:</p>
          <p className="text-blue-500">{userData.email}</p>
          <p className='font-medium'>Phone:</p>
          {
            isEdit
              ? <input className="bg-gray-100 max-w-52" type="text" value={userData.phone} onChange={e => setUserData(prev => ({ ...prev, phone: e.target.value }))} />
              : <p className="text-blue-400">{userData.phone}</p>
          }
          <p className='font-medium'>Address:</p>
          {
            isEdit
              ? <p>
                <input className="bg-gray-50" type="text" onChange={(e) => setUserData(prev => ({ ...prev, address: { ...prev.address, line1: e.target.value } }))} value={userData.address.line1} />
                <br />
                <input className="bg-gray-50" type="text" onChange={(e) => setUserData(prev => ({ ...prev, address: { ...prev.address, line2: e.target.value } }))} value={userData.address.line2} />
              </p>
              : <p className='text-gray-500'>{userData.address.line1}
                <br /> {userData.address.line2}</p>
          }



        </div>
      </div>

      <div>
        <p className="text-neutral-500 underline mt-3">BASIC INFORMATION</p>
        <div className="grid grid-cols-[1fr_3fr] gap-y-2.5 mt-3 text-neutral-700">
          <p className="font-medium">Gender</p>
          {
            isEdit
              ? <select className="max-w-20 bg-gray-100" onChange={(e) => setUserData(prev => ({ ...prev, gender: e.target.value }))} value={userData.gender}>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
              </select>
              : <p className="text-gray-400">{userData.gender}</p>
          }

          <p className="font-medium">Birthday:</p>
          {
            isEdit
              ? <input className="max-w-28 bg-gray-100" type="date" onChange={(e) => setUserData(prev => ({ ...prev, dob: e.target.value }))} value={userData.dob} />
              : <p className="text-gray-400">{userData.dob}</p>
          }

          <p className="font-medium">Age:</p>
          {
            isEdit
              ? <input className="max-w-20 bg-gray-100" type="number" onChange={(e) => setUserData(prev => ({ ...prev, age: e.target.value }))} value={userData.age} />
              : <p className="text-gray-400">{userData.age}</p>
          }
        </div>
      </div>

      <div className="mt-10">
        {
          isEdit
            ? <button className="border border-primary px-8 py-2 rounded-full hover:bg-primary hover:text-white transition-all" onClick={updateUserProfileData}>Save information</button>
            : <button className="border border-primary px-8 py-2 rounded-full hover:bg-primary hover:text-white transition-all" onClick={() => setIsEdit(true)}>Edit</button>
        }
      </div>



    </div>
  )
}

export default MyProfile


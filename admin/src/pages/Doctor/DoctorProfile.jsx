import { useEffect } from 'react'
import { useContext } from 'react';
import { DoctorContext } from '../../context/DoctorContext';
import { AppContext } from '../../context/AppContext';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { useState } from 'react';
import axios from 'axios';
import { assets } from '../../assets/assets';

const DoctorProfile = () => {

  const { dToken, profileData, setProfileData, getProfileData, backendUrl } = useContext(DoctorContext);
  const { currency } = useContext(AppContext);

  const [isEdit, setIsEdit] = useState(false);
  const [image, setImage] = useState(false);

  // Local state for editing
  const [fees, setFees] = useState(0);
  const [address1, setAddress1] = useState('');
  const [address2, setAddress2] = useState('');
  const [available, setAvailable] = useState(false);
  const [about, setAbout] = useState('');

  useEffect(() => {
    if (profileData) {
      setFees(profileData.fees);
      setAddress1(profileData.address.line1);
      setAddress2(profileData.address.line2);
      setAvailable(profileData.available);
      setAbout(profileData.about);
    }
  }, [profileData]);

  const updateProfile = async () => {
    try {
      const performUpdate = async (imageBase64) => {
          const updateData = {
              id: profileData.id,
              fees: Number(fees),
              addressLine1: address1,
              addressLine2: address2,
              available: available,
              about: about,
              image: imageBase64 || profileData.image
          };

          const { data } = await axios.post(backendUrl + '/api/doctor/update-profile', updateData, { headers: { dtoken: dToken } });
          
          if (data.success) {
              toast.success("Profile updated successfully!");
              setIsEdit(false);
              setImage(false);
              getProfileData();
          } else {
              toast.error(data.message);
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
      toast.error(error.message);
    }
  }

  useEffect(() => {
    if (dToken) {
      getProfileData();
    }
  }, [dToken]);

  return profileData && (
    <div className="m-5 w-full">
      <p className="mb-3 text-lg font-medium">My Profile</p>

      <div className="bg-white px-8 py-8 border border-zinc-200 rounded w-full max-w-4xl max-h-[80vh] overflow-y-scroll">
        
        {/* Profile Image Section */}
        <div className="flex items-center gap-4 mb-8 text-gray-500">
          <label htmlFor="doctor-image">
            <img className="w-16 h-16 bg-gray-100 rounded-full cursor-pointer object-cover" src={image ? URL.createObjectURL(image) : profileData.image} alt="" />
          </label>
          {isEdit && <input onChange={(e) => setImage(e.target.files[0])} type="file" id="doctor-image" hidden />}
          <p>{isEdit ? "Upload profile picture" : profileData.name}</p>
        </div>

        <div className="flex flex-col lg:flex-row items-start gap-10 text-gray-600">
          
          {/* Left Column */}
          <div className="w-full lg:flex-1 flex flex-col gap-4">
            
            <div className="flex-1 flex flex-col gap-1">
              <p className='font-medium text-black'>Speciality</p>
              <p className='px-3 py-2 border border-transparent bg-gray-50 rounded'>{profileData.speciality}</p>
            </div>

            <div className="flex-1 flex flex-col gap-1">
              <p className='font-medium text-black'>Degree</p>
              <p className='px-3 py-2 border border-transparent bg-gray-50 rounded'>{profileData.degree}</p>
            </div>

            <div className="flex-1 flex flex-col gap-1">
              <p className='font-medium text-black'>Experience</p>
              <p className='px-3 py-2 border border-transparent bg-gray-50 rounded'>{profileData.experience}</p>
            </div>

            <div className="flex-1 flex flex-col gap-1">
              <p className='font-medium text-black'>Fees</p>
              {isEdit 
                ? <div className='flex items-center gap-1 border border-zinc-200 rounded px-3 py-2'>
                    <span>{currency}</span>
                    <input className='outline-none w-full' type="number" onChange={(e) => setFees(e.target.value)} value={fees} />
                  </div>
                : <p className='px-3 py-2 border border-transparent bg-gray-50 rounded'>{currency}{fees}</p>
              }
            </div>

          </div>

          {/* Right Column */}
          <div className="w-full lg:flex-1 flex flex-col gap-4">
            
            <div className="flex-1 flex flex-col gap-1">
              <p className='font-medium text-black'>Address</p>
              {isEdit 
                ? <>
                    <input onChange={(e) => setAddress1(e.target.value)} value={address1} className="border border-zinc-200 rounded px-3 py-2 mb-2" type="text" placeholder="Address 1" required />
                    <input onChange={(e) => setAddress2(e.target.value)} value={address2} className="border border-zinc-200 rounded px-3 py-2" type="text" placeholder="Address 2" required />
                  </>
                : <div className='px-3 py-2 border border-transparent bg-gray-50 rounded'>
                    <p>{address1}</p>
                    <p>{address2}</p>
                  </div>
              }
            </div>

            <div className="flex items-center gap-2 pt-2">
              <input onChange={() => isEdit && setAvailable(!available)} type="checkbox" id="available" checked={available} className='cursor-pointer' />
              <label htmlFor="available" className='cursor-pointer font-medium text-black'>Available</label>
            </div>

          </div>
        </div>

        {/* About Section */}
        <div className='mt-6'>
          <p className="mb-2 font-medium text-black">About Doctor</p>
          {isEdit 
            ? <textarea onChange={(e) => setAbout(e.target.value)} value={about} className="w-full px-4 pt-2 border border-zinc-200 rounded" placeholder="Write about Doctor" rows={5} required></textarea>
            : <p className='px-4 py-3 border border-transparent bg-gray-50 rounded text-sm text-gray-600'>{about}</p>
          }
        </div>

        <div className='mt-8'>
          {isEdit 
            ? <button onClick={updateProfile} className="bg-primary py-3 px-10 text-white rounded-full hover:bg-opacity-90 transition-all">Save Changes</button>
            : <button onClick={() => setIsEdit(true)} className="bg-primary py-3 px-10 text-white rounded-full hover:bg-opacity-90 transition-all">Edit Profile</button>
          }
        </div>

      </div>
    </div>
  )
}

export default DoctorProfile

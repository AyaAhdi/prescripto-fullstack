import { useContext } from "react";
import { assets } from '../../assets/assets'
import { useState } from "react";
import { AdminContext } from "../../context/AdminContext";
import { toast } from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
import axios from 'axios';



const AddDoctor = () => {

  const [docImg, setDocImg] = useState(false)
  const [name, setName] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [experience, setExperience] = useState('1 Year')
  const [fees, setFees] = useState('')
  const [speciality, setSpeciality] = useState('General physician')
  const [degree, setDegree] = useState('')
  const [address1, setAddress1] = useState('')
  const [address2, setAddress2] = useState('')
  const [about, setAbout] = useState('')

  const { backendUrl, aToken } = useContext(AdminContext)


  const onSubmitHandler = async (event) => {
    event.preventDefault()

    try {
      if (!docImg) {
        return toast.error('Image Not Selected')
      }

      // Convert image to Base64
      const reader = new FileReader();
      reader.readAsDataURL(docImg);
      reader.onloadend = async () => {
          const base64Image = reader.result;
          
          const doctorData = {
              name,
              email,
              password,
              experience,
              fees: Number(fees),
              speciality,
              degree,
              addressLine1: address1,
              addressLine2: address2,
              about,
              image: base64Image
          };

          const { data } = await axios.post(backendUrl + '/api/admin/add-doctor', doctorData, { headers: { atoken: aToken } })
          
          if (data.success) {
              toast.success("Doctor added successfully!")
              setDocImg(false)
              setName('')
              setEmail('')
              setPassword('')
              setFees('')
              setDegree('')
              setAddress1('')
              setAddress2('')
              setAbout('')
          } else {
              toast.error(data.message)
          }
      };

    } catch (error) {
      toast.error(error.message)
      console.log(error)
    }
  }


  return (
    <form onSubmit={onSubmitHandler} className="m-5 w-full">
      <p className="mb-3 text-lg font-medium">Add Doctor</p>

      <div className="bg-white px-8 py-8 border border-zinc-200 rounded w-full max-w-4xl max-h-[80vh] overflow-y-scroll">
        <div className="flex items-center gap-4 mb-8 text-gray-500">
          <label htmlFor="doctor-image">
            <img className="w-16 bg-gray-100 rounded-full cursor-pointer" src={docImg ? URL.createObjectURL(docImg) : assets.upload_area} alt="" />
          </label>
          <input onChange={(e) => setDocImg(e.target.files[0])} type="file" id="doctor-image" hidden />
          <p>Upload doctor <br /> picture</p>
        </div>

        <div className="flex flex-col lg:flex-row items-start gap-10 text-gray-600">
          <div className="w-full lg:flex-1 flex flex-col gap-4">

            <div className="flex-1 flex flex-col gap-1">
              <p>Doctor name</p>
              <input onChange={(e) => setName(e.target.value)} value={name} className="border border-zinc-200 rounded px-3 py-2" type="text" placeholder="Name" required />
            </div>

            <div className="flex-1 flex flex-col gap-1">
              <p>Doctor Email</p>
              <input onChange={(e) => setEmail(e.target.value)} value={email} className="border border-zinc-200 rounded px-3 py-2" type="email" placeholder="Email" required autoComplete="off" />
            </div>

            <div className="flex-1 flex flex-col gap-1">
              <p>Doctor Password</p>
              <input onChange={(e) => setPassword(e.target.value)} value={password} className="border border-zinc-200 rounded px-3 py-2" type="password" placeholder="Password" required autoComplete="new-password" />
            </div>

            <div className="flex-1 flex flex-col gap-1">
              <p>Experience</p>
              <select onChange={(e) => setExperience(e.target.value)} value={experience} className="border border-zinc-200 rounded px-3 py-2" name="" id="">
                <option value="1 year">1 years</option>
                <option value="2 year">2 years</option>
                <option value="3 year">3 years</option>
                <option value="4 year">4 years</option>
                <option value="5 year">5 years</option>
                <option value="6 year">6 years</option>
                <option value="7 year">7 years</option>
                <option value="8 year">8 years</option>
                <option value="9 year">9 years</option>
                <option value="10 year">10 years</option>
              </select>
            </div>

            <div className="flex-1 flex flex-col gap-1">
              <p>Fees</p>
              <input onChange={(e) => setFees(e.target.value)} value={fees} className="border border-zinc-200 rounded px-3 py-2" type="number" placeholder="Your fees" required id="" />
            </div>

          </div>

          <div className="w-full lg:flex-1 flex flex-col gap-4">
            <div className="flex-1 flex flex-col gap-1">
              <p>Speciality</p>
              <select onChange={(e) => setSpeciality(e.target.value)} value={speciality} className="border border-zinc-200 rounded px-3 py-2" name="" id="">
                <option value="General physician">General physician</option>
                <option value="Gynecologist">Gynecologist</option>
                <option value="Dermatologist">Dermatologist</option>
                <option value="Pediatricians">Pediatricians</option>
                <option value="Neurologist">Neurologist</option>
                <option value="Gastroenterologist">Gastroenterologist</option>
              </select>
            </div>

            <div className="flex-1 flex flex-col gap-1">
              <p>Education</p>
              <input onChange={(e) => setDegree(e.target.value)} value={degree} className="border border-zinc-200 rounded px-3 py-2" type="text" placeholder="Education" required />
            </div>

            <div className="flex-1 flex flex-col gap-1">
              <p>Address</p>
              <input onChange={(e) => setAddress1(e.target.value)} value={address1} className="border border-zinc-200 rounded px-3 py-2" type="text" placeholder="Address 1" required />
              <input onChange={(e) => setAddress2(e.target.value)} value={address2} className="border border-zinc-200 rounded px-3 py-2" type="text" placeholder="Address 2" required />
            </div>

          </div>
        </div>

        <div>
          <p className="mt-4 mb-2">About Doctor</p>
          <textarea onChange={(e) => setAbout(e.target.value)} value={about} className="w-full px-4 pt-2 border border-zinc-200 rounded" placeholder="Write about Doctor" rows={5} required></textarea>
        </div>

        <button type="submit" className="bg-primary py-3 px-10 mt-4 text-white rounded-full">Add Doctor</button>

      </div>
    </form>

  );
};

export default AddDoctor;
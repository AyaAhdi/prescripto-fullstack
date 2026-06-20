import { useEffect } from 'react'
import { useState, useContext } from 'react'
import { AppContext } from '../context/AppContext'
import axios from 'axios'
import { toast } from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css';
import { useNavigate } from 'react-router-dom'

const Login = () => {


  const { backendUrl, token, setToken } = useContext(AppContext)

  const navigate = useNavigate()

  const [state, setState] = useState('Sign Up')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [name, setName] = useState('')

  const onSubmitHandler = async (event) => {
    event.preventDefault()
    try {
      if (state === 'Sign Up') {
        const { data } = await axios.post(backendUrl + '/api/user/register', { name, email, password })
        if (data.success) {
          toast.success(data.message)
          setState('Login')
        } else {
          toast.error(data.message)
        }
      } else {
        const { data } = await axios.post(backendUrl + '/api/user/login', { email, password })
        if (data.success) {
          localStorage.setItem('token', data.token)
          setToken(data.token)
          toast.success("Logged in successfully!")
        } else {
          toast.error(data.message)
        }
      }
    } catch (error) {
      toast.error(error.message)
    }
  }

  useEffect(() => {
    if (token) {
      navigate('/')
    }
  }, [token])

  return (
    <form onSubmit={onSubmitHandler} className='min-h-[80vh] flex items-center'>
      <div className='flex flex-col gap-3 m-auto border border-zinc-300 items-start p-8 min-w-85 sm:min-w-96 rounded-xl text-zinc-600 text-sm shadow-lg'>
        <p className="text-2xl font-semibold">{state === 'Sign Up' ? 'Create Account' : 'Login'}</p>
        <p>Please {state === 'Sign Up' ? 'sign up' : 'log in'} to book appointments</p>
        {state === "Sign Up" &&
          <div className="w-full">
            <p>Name</p>
            <input
              className="border border-zinc-300 rounded w-full p-2 mt-1"
              type='name'
              placeholder='john doe'
              value={name}
              onChange={(e) => setName(e.target.value)}
              required />
          </div>
        }

        <div className="w-full">
          <p>Email</p>
          <input
            className="border border-zinc-300 rounded w-full p-2 mt-1"
            type='email'
            placeholder='john.doe@example.com'
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <div className="w-full">
          <p>Password</p>
          <input
            className="border border-zinc-300 rounded p-2 mt-1 w-full"
            type='password'
            placeholder='••••••••'
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit" className="bg-primary text-white w-full py-2 rounded-md text-base">{state === 'Sign Up' ? 'Create Account' : 'Login'}</button>
        {state === "Sign Up"
          ? <p> Already Have An Account? <span onClick={() => setState('Login')} className="text-primary underline cursor-pointer">Login here</span></p>
          : <p>Create An New Account? <span onClick={() => setState('Sign Up')} className="text-primary underline cursor-pointer" >click here</span></p>
        }
      </div>
    </form>
  )
}

export default Login


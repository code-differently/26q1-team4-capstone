import { createContext, useContext, useState, useEffect } from 'react';

const AuthContext = createContext(null)
export function AuthProvider({ children }) {
const [user, setUser] = useState(null)
const [skillProfile, setSkillProfile] = useState(null)
const [isAuthenticated, setIsAuthenticated] = useState(false)
const [isLoading, setIsLoading] = useState(true)

useEffect(() => {
}, [])



}

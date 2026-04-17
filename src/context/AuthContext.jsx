import { createContext, useContext, useState, useEffect } from 'react';

const AuthContext = createContext(null)
export function AuthProvider({ children }) {
    const [user, setUser] = useState(null)
    const [skillProfile, setSkillProfile] = useState(null)
    const [isAuthenticated, setIsAuthenticated] = useState(false)
    const [isLoading, setIsLoading] = useState(true)

    useEffect(() => {
        const saved = localStorage.getItem('nodus_user')
        if (saved) {
            setUser(JSON.parse(saved))
            setIsAuthenticated(true)
        }
        setIsLoading(false)
    }, [])

    const login = (accountInfo) => {
        setUser(accountInfo)
        setIsAuthenticated(true)
        localStorage.setItem('nodus_user', JSON.stringify(accountInfo))
    }

    const logout = () => {
        setUser(null)
        setIsAuthenticated(false)
        localStorage.removeItem('nodus_user')
    }

    const updateSkillProfile = (updatedList) => {
        setSkillProfile(updatedList)
    }

    return (
        <AuthContext.Provider value={{
            user,
            skillProfile,
            isAuthenticated,
            isLoading,
            login,
            logout,
            updateSkillProfile
        }}>
            {children}
        </AuthContext.Provider>
    )

}

export const useAuth = () => useContext(AuthContext)
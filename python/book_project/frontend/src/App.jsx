import {Navigate, Route, Routes} from 'react-router-dom'
import LoginPage from './pages/LoginPage'
import BooksPage from './pages/BooksPage'
import PublishersPage from './pages/PublishersPage'
import AuthorsPage from './pages/AuthorsPage'
import AnalyticsPage from './pages/AnalyticsPage'
import BorrowDemo from "./pages/BorrowDemo.jsx";
import RecommendationsPage from './pages/RecommendationsPage'

export default function App() {
    return (
        <Routes>
            <Route path="/" element={<Navigate to="/login" replace/>}/>
            <Route path="/login" element={<LoginPage/>}/>
            <Route path="/books" element={<BooksPage/>}/>
            <Route path="/publishers" element={<PublishersPage/>}/>
            <Route path="/authors" element={<AuthorsPage/>}/>
            <Route path="/analytics" element={<AnalyticsPage/>}/>
            <Route path="/borrow" element={<BorrowDemo/>}/>
            <Route path="/recommendations" element={<RecommendationsPage/>}/>
            <Route path="*" element={<Navigate to="/login" replace/>}/>
        </Routes>
    )
}

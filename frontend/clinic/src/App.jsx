import axios from 'axios';
import { useState, useEffect } from 'react';
import Header from './component/header/Header.jsx';
import PatientList from './component/patient-list/PatientList.jsx';
import PaginationControls from './component/controls/PaginationControls.jsx';
import './App.css'

function App() {
  const [patients, setPatients] = useState([]);
  const [updateFlag, setUpdateFlag] = useState(true);
  const [limit, setLimit] = useState(10);
  const [page, setPage] = useState(0);

  useEffect(() => {
    axios.get(`http://localhost:8080/api/v1/patient/list?limit=${limit}&offset=${page * limit}`)
      .then(response => setPatients(response.data))
      .catch(error => console.log(error.response.data))
      .finally(() => setUpdateFlag(false));
  }, [updateFlag, limit, page]);

  function updatePatientList() {
    setUpdateFlag(true);
  }

  function previousPage() {
    if (page > 0) {
      setPage(page - 1);
    }
  }

  function nextPage() {
    if (patients.length === limit) {
      setPage(page + 1);
    }
  }

  return (
    <>
      <Header updatePatientList={ updatePatientList } />
      <div className='main-section'>
        <h1>Showing patients {Math.min(page * limit + 1, patients.length)} to {Math.min((page + 1) * limit, page * limit + patients.length)}:</h1>
        <PatientList patients={ patients } updatePatientList={ updatePatientList } />
      </div>
      <PaginationControls previousPage={previousPage} nextPage={nextPage} />
    </>
  );
}

export default App

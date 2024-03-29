import axios from 'axios';
import { useState } from 'react';
import PatientForm from '../patient-form/PatientForm.jsx';
import './header.css';

export default function Header({ updatePatientList }) {
    const [displayForm, setDisplayForm] = useState(false);
    
    function handleFormSubmit(patient) {
        if (patient !== null) {
            axios.post('http://localhost:8080/api/v1/patient/create', patient)
                .then(() => updatePatientList())
                .catch(error => console.log(error.response.data));
        }
        setDisplayForm(false);
    }

    return (
        <header className='header'>
            <h2 className='name'>Patient Management Tool</h2>
            <button className='add-patient' onClick={() => setDisplayForm(true)}>Add patient</button>

            <PatientForm submitHandler={ handleFormSubmit } display={displayForm} />
        </header>
    )
}
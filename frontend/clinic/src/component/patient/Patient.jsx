import axios from 'axios';
import { useState } from "react";
import PatientForm from "../patient-form/PatientForm";
import './patient.css';

export default function Patient({ patient, updatePatientList }) {
    const [displayForm, setDisplayForm] = useState(false);

    function handleEdit(patient) {
        if (patient !== null) {
            axios.put(
                'http://localhost:8080/api/v1/patient/update',
                {
                    identificationPESEL: patient.PESEL,
                    patient: patient
                })
                .then(() => updatePatientList())
                .catch(error => console.log(error.response.data));
        }
        setDisplayForm(false);
    }

    function handleDelete() {
        axios.delete('http://localhost:8080/api/v1/patient/delete', {data: patient})
            .then(() => updatePatientList())
            .catch(error => console.log(error.response.data));
    }

    return (
        <div>
            <div className="patient-info">
                <div >{ patient.firstName }</div>
                <div >{ patient.lastName }</div>
                <div >{ patient.PESEL }</div>
                <div >{ patient.address.street }</div>
                <div >{ patient.address.city }</div>
                <div >{ patient.address.zipCode }</div>

                <button className='button-edit' type='button' onClick={() => setDisplayForm(true)}>Edit</button>
            <button className='button-delete' type='button' onClick={() => handleDelete()}>Delete</button>
            </div>
        
            <PatientForm submitHandler={ handleEdit } display={displayForm} defaultValues={ patient } />
        </div>
    );
}
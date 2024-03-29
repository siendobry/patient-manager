import Patient from '../patient/Patient.jsx';
import './patient-list.css'

export default function PatientList({ patients, updatePatientList }) {
    const patientsMapped = patients.map(patient => {
        return (
            <li
                key={patient.PESEL}
                className='patient-element'
            >
                <Patient patient={patient} updatePatientList={ updatePatientList } />
            </li>
        );
    });

    return (
        <ul className='patient-list'>{ patientsMapped }</ul>
    )

}
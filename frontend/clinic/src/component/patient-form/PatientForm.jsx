import './patient-form.css';

export default function PatientForm({ submitHandler, display, defaultValues={address: {}} }) {
    function handleSubmit(event) {
        event.preventDefault();

        const formData = new FormData(event.target);
        const patient = {
            firstName: formData.get('first-name'),
            lastName: formData.get('last-name'),
            PESEL: formData.get('pesel'),
            address: {
                street: formData.get('street'),
                city: formData.get('city'),
                zipCode: formData.get('zip-code')
            }
        }

        if (validateForm(patient)) {
            submitHandler(patient);
        } else {
            submitHandler(null);
        }
    }

    function validateForm(form) {
        for (let input of Object.values(form)) {
            if (input.value === '') {
                return false;
            }
        }
        return true;
    }

    return (
        <div style={{display: display ? 'flex' : 'none'}} className='patient-form-wrapper'>
            <form className='patient-form'onSubmit={handleSubmit}>
                <h2>Add new patient</h2>
                <div>
                    <label htmlFor='first-name'>First Name</label>
                    <input name='first-name' id='first-name' type='text' defaultValue={defaultValues.firstName} />
                </div>
                <div>
                    <label htmlFor='last-name'>Last Name</label>
                    <input name='last-name' id='last-name' type='text' defaultValue={defaultValues.lastName} />
                </div>
                <div>
                    <label htmlFor='pesel'>PESEL</label>
                    <input name='pesel' id='pesel' type='text' defaultValue={defaultValues.PESEL} />
                </div>
                <div>
                    <label htmlFor='street'>Street</label>
                    <input name='street' id='street' type='text' defaultValue={defaultValues.address.street} />
                </div>
                <div>
                    <label htmlFor='city'>City</label>
                    <input name='city' id='city' type='text' defaultValue={defaultValues.address.city} />
                </div>
                <div>
                    <label htmlFor='zip-code'>Zip code</label>
                    <input name='zip-code' id='zip-code' type='text' defaultValue={defaultValues.address.zipCode} />
                </div>
                <button type='submit'>Submit</button>
            </form>
        </div>
    );

}
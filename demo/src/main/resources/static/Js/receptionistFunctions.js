const API = 'http://localhost:8080/api/recepcionista';
// Objeto para almacenar el estado del paciente
const patientState = {
    currentRun: null
};

// Función para establecer el valor del run
function setCurrentRunPatient(run) {
    patientState.currentRun = run;
}

// Función para obtener el valor del run
function getCurrentRunPatient() {
    return patientState.currentRun;
}
    

document.addEventListener('DOMContentLoaded', () => { 
    const fetchPatientBtn = document.getElementById('fetchPatientBtn');
    const openCreatePatient = document.getElementById('openCreatePatient');

    const formCreatePatient = document.getElementById('formCreatePatient'); // DIV
    const createPatientForm = document.getElementById('createPatientForm'); // FORM

    const closeCreatePatientBtn = document.getElementById('closeCreatePatientBtn');

    const errorContainerPatient = document.getElementById('errorContainerPatient');


    const updatePatientForm = document.getElementById('updatePatientForm'); // FORM
    const formUpdatePatient = document.getElementById('formUpdatePatient'); // DIV
    const errorContainerUpdatePatient = document.getElementById('errorContainerUpdatePatient');
    const updateErrorContainerPatient = document.getElementById('updateErrorContainerPatient');
    const updatePatientBtn = document.getElementById('updatePatientBtn'); 
    const closeUpdatePatientBtn = document.getElementById('closeUpdatePatientBtn'); 


    const closeAppointmentBtn = document.getElementById('closeAppointmentBtn');
    const closeAllPatientAppointmentBtn = document.getElementById('closeAllPatientAppointmentBtn');


    // Abrir Ventana emergente de Crear Paciente
    openCreatePatient.addEventListener('click', () => {
        cleanForm('create');
        loadSexSelect('3');
        loadHealthInsuranceSelect('0');
        closeAppointmentBtn.click(); 
        closeUpdatePatientBtn.click(); 
        closePaymentBtn.click();
        closeAllPatientAppointmentBtn.click();
        containerAppointmentsOfTheDay.style.display = 'none';
        formCreatePatient.style.display = 'block';
    });

    // Enviar formulario de Crear Paciente
    createPatientForm.addEventListener('submit', (event) => {
        event.preventDefault();  
        errorContainerPatient.innerHTML = ''; 
        createPatient1();
    });

    // Cerrar Ventana emergende de Crear Paciente
    closeCreatePatientBtn.addEventListener('click', () => { 
        formCreatePatient.style.display = 'none';    
        cleanForm('create');
    });
    
    // Limpiar el formulario de crear paciente y Modificar Paciente
    function cleanForm(type) {
        if (type === 'create') {
        createPatientForm.reset();
        errorContainerPatient.innerHTML = ''; 
        } 
        else if (type === 'update') {
            updatePatientForm.reset();
            updateErrorContainerPatient.innerHTML = ''; 
        }
    }

    // Function para Cargar Los Sexos de Crear Usuario
    async function loadSexSelect(lugar, selectedId = null) {
        try {
            const response = await fetch(`${API}/sexs`);
            if (!response.ok) {
                throw new Error('Error en la respuesta de la API');
            }

            const sexs = await response.json();
            const select = document.getElementById('userSex' + lugar);

            select.innerHTML = '';

            sexs.forEach((sex) => {
                const option = document.createElement('option');
                option.value = sex.idSex;
                option.textContent = sex.nameSex;
                if (sex.idSex === selectedId) {
                    option.selected = true;
                }
                select.appendChild(option);
            });
        } catch (error) {
            console.error('Error al obtener los sexos:', error);
        }
    }


    // Function para Cargar Las Previsiones médicas de Crear Usuario
    async function loadHealthInsuranceSelect(lugar, selectedId = null) {
        try {
            const response = await fetch(`${API}/prevision/all`);
            if (!response.ok) {
                throw new Error('Error en la respuesta de la API');
            }

            const healthInsurances = await response.json();
            const select = document.getElementById('PatientHealthInsurance' + lugar);

            select.innerHTML = '';

            healthInsurances.forEach((healthInsurance) => {
                const option = document.createElement('option');
                option.value = healthInsurance.idHealthInsurance;
                option.textContent = healthInsurance.nameHealthInsurance;
                if (healthInsurance.idHealthInsurance === selectedId) {
                    option.selected = true;
                }
                select.appendChild(option);
            });
        } catch (error) {
            console.error('Error al obtener los sexos:', error);
        }
    }    


    // Crear Paciente
    async function createPatient1() {

        const rawSex = document.getElementById('userSex3').value;
        const rawInsurance = document.getElementById('PatientHealthInsurance0').value;

        const newPatient = {
            runPatient: document.getElementById('runPatient').value,
            firstnamePatient: document.getElementById('firstnamePatient').value,
            lastnamePatient1: document.getElementById('lastnamePatient1').value,
            lastnamePatient2: document.getElementById('lastnamePatient2').value,
            bornDatePatient: document.getElementById('bornDatePatient').value,
            phonePatient: document.getElementById('phonePatient').value,
            emailPatient: document.getElementById('emailPatient').value,
            idSexPatient: rawSex,
            idHealthInsurancePatient: rawInsurance 
        };

        const errors = validateForm(newPatient, 'create');
        // Si hay errores, mostrar los mensajes de error
        if (errors.length > 0) {
            errorContainerPatient.innerHTML = `<ul>${errors.map(error => `<li>${error}</li>`).join('')}</ul>`;
            return;
        }

        // Si no hay errores, continuar con la creación del Paciente
        createPatient2(newPatient);        
    };

    // Crear Paciente 2
    async function createPatient2(newPatient) {
        try {
            const response = await fetch(`${API}/create-patient`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(newPatient)
            });
    
            const data = await response.json();
    
            if (!response.ok) {
                if (data.errors) {
                    let errorMessage = '<ul>';
                    for (const [campo, error] of Object.entries(data.errors)) {
                        errorMessage += `<li><strong>${campo}:</strong> ${error}</li>`;
                    }
                    errorMessage += '</ul>';
                    errorContainerPatient.innerHTML = errorMessage;
                } else {
                    alert(data.message || 'Error desconocido al crear el paciente');
                }
                return;
            }
    
            // Éxito
            alert(data.message); // "Paciente creado correctamente"
            cleanForm('create');
            formCreatePatient.style.display = 'none';
            await fetchPatient(newPatient.runPatient); 
    
        } catch (error) {
            alert("Ocurrió un error al crear el paciente");
        }
    }

    // Validar Crear Paciente
    function validateForm(Patient, type) {
        const errors = [];

        if (type === 'create') {
            const runError = validateRun(Patient.runPatient, 'create');
            if (runError) errors.push(runError);

            const firstnameError = validateFirstname(Patient.firstnamePatient, 'create');
            if (firstnameError) errors.push(firstnameError);

            const lastname1Error = validateLastname1(Patient.lastnamePatient1,'create');
            if (lastname1Error) errors.push(lastname1Error);

            const emailError = validateEmail(Patient.emailPatient, 'create');
            if (emailError) errors.push(emailError);

            const phoneError = validatePhone(Patient.phonePatient, 'create');
            if (phoneError) errors.push(phoneError);

            const bornDateError = validateBornDate(Patient.bornDatePatient, 'create');
            if (bornDateError) errors.push(bornDateError);

            const healthInsuranceError = validateHealthInsurance(Patient.idHealthInsurancePatient);
            if (healthInsuranceError) errors.push(healthInsuranceError);

            const sexError = validateSex(Patient.idSexPatient);
            if (sexError) errors.push(sexError);
        }
       
        if (type === 'update') {
            const runError = validateRun(Patient.runPatient, 'update');
            if (runError) errors.push(runError);

            const firstnameError = validateFirstname(Patient.firstnamePatient, 'update');
            if (firstnameError) errors.push(firstnameError);

            const lastname1Error = validateLastname1(Patient.lastnamePatient1, 'update');
            if (lastname1Error) errors.push(lastname1Error);

            const emailError = validateEmail(Patient.emailPatient, 'update');
            if (emailError) errors.push(emailError);

            const phoneError = validatePhone(Patient.phonePatient, 'update');
            if (phoneError) errors.push(phoneError);

            const healthInsuranceError = validateHealthInsurance(Patient.idHealthInsurancePatient);
            if (healthInsuranceError) errors.push(healthInsuranceError);

            const sexError = validateSex(Patient.idSexPatient);
            if (sexError) errors.push(sexError);
        }
        return errors;
    }

    // Valida Run 
    function validateRun(run, type) {
        const regex = /^[0-9]{7,8}-[0-9kK]{1}$/;
    
        if (type === 'update') {
            if (!run) return null; 
            if (!regex.test(run)) {
                return "El RUN no es válido";
            }
        }
    
        if (type === 'create') {
            if (!run) {
                return "El RUN es obligatorio";
            }
            if (!regex.test(run)) {
                return "El RUN no es válido";
            }
        }
        return null;
    }
    
    // Valida Nombre
    function validateFirstname(firstname, type) {
        if (type === 'create') {
            if (!firstname) {
                return "El nombre es obligatorio";
            }
            if (firstname.length < 2 || firstname.length > 50) {
                return "El nombre debe tener entre 2 y 50 caracteres";
            }
        }
    
        if (type === 'update') {
            if (!firstname) return null; 
            if (firstname.length < 2 || firstname.length > 50) {
                return "El nombre debe tener entre 2 y 50 caracteres";
            }
        }
    
        return null;
    }

    // Valida Apellido Paterno
    function validateLastname1(lastname1, type) {
        if (type === 'create') {
            if (!lastname1) {
                return "El primer apellido es obligatorio";
            }
            if (lastname1.length < 2 || lastname1.length > 50) {
                return "El primer apellido debe tener entre 2 y 50 caracteres";
            }
        }
    
        if (type === 'update') {
            if (!lastname1) return null;
            if (lastname1.length < 2 || lastname1.length > 50) {
                return "El primer apellido debe tener entre 2 y 50 caracteres";
            }
        }
        return null;
    }
    
    // Valida Email
    function validateEmail(email, type) {
        const regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    
        if (type === 'create') {
            if (!email) {
                return "El correo electrónico es obligatorio";
            }
            if (!regex.test(email)) {
                return "El correo electrónico no es válido";
            }
        }
    
        if (type === 'update') {
            if (!email) return null;
            if (!regex.test(email)) {
                return "El correo electrónico no es válido";
            }
        }
        return null;
    }

    // Valida Teléfono
    function validatePhone(phone, type) {
        const regex = /^\d{9}$/;
    
        if (type === 'create') {
            if (!phone) {
                return "El teléfono no puede estar vacío";
            }
            if (phone.length !== 9) {
                return "El teléfono debe tener exactamente 9 caracteres";
            }
            if (!regex.test(phone)) {
                return "El teléfono debe contener solo números";
            }
        }
    
        if (type === 'update') {
            if (!phone) return null;
            if (phone.length !== 9) {
                return "El teléfono debe tener exactamente 9 caracteres";
            }
            if (!regex.test(phone)) {
                return "El teléfono debe contener solo números";
            }
        }
        return null;
    }

    // Valida Fecha de Nacimiento
    function validateBornDate(bornDate) {
        if (!bornDate) {
            return "La fecha de nacimiento es obligatoria.";
        }
        const today = new Date();
        const birthDate = new Date(bornDate);
        if (birthDate >= today) {
            return "La fecha de nacimiento debe estar en el pasado.";
        }
        return null;
    }

    // Valida Previsión Médica
    function validateHealthInsurance(idHealthInsurance) {

        if (idHealthInsurance == null) {
            return "La previsión médica es obligatoria.";
        }
        return null;
    }

    // Valida Sexo
    function validateSex(idSex) {
        if (idSex == null) {
            return "El sexo es obligatorio.";
        }
        return null;
    }

    // Buscar Paciente por Run
    fetchPatientBtn.addEventListener('click', () => {
        closeAppointmentBtn.click(); 
        closeUpdatePatientBtn.click();
        closeCreatePatientBtn.click();
        closeAllPatientAppointmentBtn.click();
        closePaymentBtn.click();
        closePaymentBtn.click();
        containerAppointmentsOfTheDay.style.display = 'none';
        const runPatient = document.getElementById('fetchPatientByRun').value;
        validateRun(runPatient)
        if (runPatient) {
            fetchPatient(runPatient);
        } else {
            const errorDiv = document.getElementById('errorDashboardPatient');
            errorDiv.textContent = "Por favor, ingrese un RUN para buscar.";
            setTimeout(() => {
                errorDiv.textContent = '';
            }, 5000);
        }
    });

    // Crear Tabla de Pacientes
    function createTablePatient(patients) {
        patientTableBody.innerHTML = ''; 
        containerTablePatient.style.display = 'block'; 
    
        patients.forEach((patient) => {
            const row = document.createElement('tr');
    
            row.innerHTML = `
                <td>${patient.runPatient}</td>
                <td>${patient.fullnamePatient}</td>
                <td>${patient.emailPatient}</td>
                <td>${patient.phonePatient}</td>
                <td>${patient.nameHealthInsurance}</td>
                <td>${patient.nameSex}</td>
                <td>
                    <button 
                        class="btn btn-sm btn-primary btn-update" 
                        data-run="${patient.runPatient}">
                        <i class="fas fa-edit"></i> Editar
                    </button>
                    <button 
                        class="btn btn-sm btn-danger btn-delete" 
                        data-run="${patient.runPatient}">
                        <i class="fas fa-trash-alt"></i> Borrar
                    </button>
                </td>
                <td>
                    <button 
                        class="btn btn-sm btn-success btn-schedule" 
                        data-run="${patient.runPatient}">
                        <i class="fas fa-calendar-plus"></i> Agendar
                    </button>
                    <button 
                        class="btn btn-sm btn-info btn-view-appointments" 
                        data-run="${patient.runPatient}">
                        <i class="fas fa-calendar-alt"></i> Ver Citas
                    </button>
                </td>
            `;
    
            patientTableBody.appendChild(row);
        });
    }
    

    
    // Se trae al paciente y se muestra en la tabla
    async function fetchPatient(runPatient) {
        try {
            const response = await fetch(`${API}/${runPatient}`);
            if (!response.ok) {
                throw new Error('Error en la respuesta de la API');
            }
    
            const patient = await response.json();
    
            // Limpiar mensaje de error si había
            const errorDiv = document.getElementById('errorDashboardPatient');
            errorDiv.textContent = '';
    
            createTablePatient([patient]); 
        } catch (error) {
            const errorDiv = document.getElementById('errorDashboardPatient');
            errorDiv.textContent = "No se encontraron pacientes con ese RUN.";
            console.error('Error al obtener el paciente:', error);
    
            // Ocultar tabla si hay error
            containerTablePatient.style.display = 'none';
    
            // Desaparecer mensaje luego de 5 segundos
            setTimeout(() => {
                errorDiv.textContent = '';
            }, 5000);
        }
    }

    document.addEventListener('click', async function(event) {
        const updateBtn = event.target.closest('.btn-update');
        if (updateBtn) {
            const run = updateBtn.dataset.run;
            cleanForm('update');
            closeAppointmentBtn.click();
            closeCreatePatientBtn.click();
            closeAllPatientAppointmentBtn.click();
            closePaymentBtn.click();
            formUpdatePatient.style.display = 'block';
            containerAppointmentsOfTheDay.style.display = 'none';
            await getIdSexAndIdHealthInsurance(run);
            setCurrentRunPatient(run)
            
        }
    
        const deleteBtn = event.target.closest('.btn-delete');
        if (deleteBtn) {
            const run = deleteBtn.dataset.run;
            await deletePatient(run);
        }
    
        const scheduleBtn = event.target.closest('.btn-schedule');
        if (scheduleBtn) {
            const run = scheduleBtn.dataset.run;
            closeCreatePatientBtn.click();
            closeAllPatientAppointmentBtn.click();
            closeUpdatePatientBtn.click();
            closePaymentBtn.click();
            containerAppointmentsOfTheDay.style.display = 'none';
            await scheduleAppointment(run);
            setCurrentRunPatient(run)
        }
    
        const viewAppointmentsBtn = event.target.closest('.btn-view-appointments');
        if (viewAppointmentsBtn) {
            const run = viewAppointmentsBtn.dataset.run;
            const containerAllAppointments = document.getElementById('containerAllAppointments');
            containerAllAppointments.style.display = 'block';
            closeCreatePatientBtn.click();
            closeUpdatePatientBtn.click();
            closeAppointmentBtn.click();
            closePaymentBtn.click();
            containerAppointmentsOfTheDay.style.display = 'none';
            await fetchPatientAppointments(run);
            setCurrentRunPatient(run)
        }
    });

    updatePatientBtn.onclick = async function(event) {
        event.preventDefault(); 

        const run = getCurrentRunPatient(); // Obtenemos el run actual
        if (!run) {
            alert('No se ha seleccionado un paciente. Intenta nuevamente.');
            return; 
        }
    
        await updatePatient1(run); 
        closeAppointmentBtn.click(); 
    };

    
    // Obtener Sexo y Previsión Médica del paciente (para cargar los selects con los valores actuales)
    async function getIdSexAndIdHealthInsurance(run) {
        try {
            const response = await fetch(`${API}/getSexAndHealthInsurance/${run}`);
            if (!response.ok) {
                throw new Error('Error en la respuesta de la API');
            }

            const patient = await response.json();

            // Limpiar mensaje de error si había
            const errorDiv = document.getElementById('errorDashboardPatient');
            errorDiv.textContent = '';

            const idSexPatient = patient.idSexPatient;
            const idHealthInsurancePatient = patient.idHealthInsurancePatient;

            await loadSexSelect('4', idSexPatient);
            await loadHealthInsuranceSelect('1', idHealthInsurancePatient);

        } catch (error) {
            console.error('Error al obtener el paciente:', error);
        }
    }

    // Validar y preparar datos del formulario antes de actualizar
    async function updatePatient1(run) {


        const updatedPatient = {
            runPatient: document.getElementById('updateRunPatient').value.trim() || null,
            firstnamePatient: document.getElementById('updateFirstnamePatient').value.trim() || null,
            lastnamePatient1: document.getElementById('updateLastnamePatient1').value.trim() || null,
            lastnamePatient2: document.getElementById('updateLastnamePatient2').value.trim() || null,
            emailPatient: document.getElementById('updateEmailPatient').value.trim() || null,
            phonePatient: document.getElementById('updatePhonePatient').value.trim() || null,
            idHealthInsurancePatient: parseInt(document.getElementById('PatientHealthInsurance1').value),
            idSexPatient: parseInt(document.getElementById('userSex4').value)
        };

        const errors = validateForm(updatedPatient, 'update');
        if (errors.length > 0) {
            updateErrorContainerPatient.innerHTML = `<ul>${errors.map(error => `<li>${error}</li>`).join('')}</ul>`;
            return;
        }


        await updatePatient2(run, updatedPatient);
    }

    // Hacer el request PUT para actualizar el paciente
    async function updatePatient2(run, updatedPatient) {

        try {
            const response = await fetch(`${API}/update-patient/${run}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(updatedPatient)
            });

            const data = await response.json();

            if (!response.ok) {
                if (data.errors) {
                    let errorMessage = '<ul>';
                    for (const [campo, error] of Object.entries(data.errors)) {
                        errorMessage += `<li><strong>${campo}:</strong> ${error}</li>`;
                    }
                    errorMessage += '</ul>';
                    errorContainerUpdatePatient.innerHTML = errorMessage;
                } else {
                    alert(data.message || 'Error desconocido al actualizar el paciente');
                }
                return;
            }

            // Éxito
            alert(data.message);
            formUpdatePatient.style.display = 'none';
            cleanForm('update');

        } catch (error) {
            alert("Ocurrió un error al actualizar el paciente");
            console.error(error);
        }
    }

    // Cerrar formulario de actualización
    closeUpdatePatientBtn.addEventListener('click', (event) => {
        event.preventDefault();
        formUpdatePatient.style.display = 'none';
        cleanForm('update');
    });



    // Borrar Paciente
    async function deletePatient(run) {
        if (confirm('¿Estás seguro de que deseas eliminar este paciente?')) {
            try {
                const response = await fetch(`${API}/${run}`, {
                    method: 'DELETE'
                });
    
                if (!response.ok) {
                    throw new Error('Error al eliminar el paciente');
                }
    
                const errorDiv = document.getElementById('errorDashboardPatient');
                errorDiv.textContent = "Paciente borrado con éxito.";
                setTimeout(() => {
                    errorDiv.textContent = '';
                }, 5000);
    
                patientTableBody.innerHTML = ''; 
                containerTablePatient.style.display = 'none'; 
    
            } catch (error) {
                alert('Error al eliminar el paciente: ' + error.message);
            }
        }
    }
    

    // Function para Traer a todos los médicos a un select
    async function loadDoctorsSelect() {
        try {
            const response = await fetch(`${API}/doctors`);
            if (!response.ok) {
                throw new Error('Error en la respuesta de la API');
            }
    
            const doctors = await response.json();
            const select = document.getElementById('doctorSelect');
    
            // Limpiar las opciones previas
            select.innerHTML = '';
    
            // Añadir la opción "Todos los médicos"
            const allOption = document.createElement('option');
            allOption.value = '0';
            allOption.textContent = 'Todos los médicos';
            select.appendChild(allOption);
    
            // Agregar las opciones de los médicos
            doctors.forEach((doctor) => {
                const option = document.createElement('option');
                option.value = doctor.idUser;  
                option.textContent = doctor.firstNameUser + " " + doctor.lastNameUser1;  
                select.appendChild(option);
            });
        } catch (error) {
            console.error('Error al obtener los médicos:', error);
        }
    }

    // Traer citas del paciente      
      let filtroBtnInitialized = false;

    function scheduleAppointment(run) {
        const containerAppointment = document.getElementById('containerAppointment');
        containerAppointment.style.display = 'block';
        containerAppointmentsOfTheDay.style.display = 'none';
        setCurrentRunPatient(run);
      
        loadDoctorsSelect().then(() => {
            const filtroBtn = document.getElementById('filtroBtn');
      
        if (!filtroBtnInitialized) {
            filtroBtn.addEventListener('click', async () => {
            const filter = {
                runPatient: getCurrentRunPatient(run),
                idUser: document.getElementById('doctorSelect').value,
                initialDate: document.getElementById('initialDate').value,
                finalDate: document.getElementById('finalDate').value
            };
      
            if (isPastDate(filter.initialDate)) {
                alert("La fecha inicial no puede ser menor a la fecha actual.");
                return;
            }
      
            if (isPastDate(filter.finalDate)) {
                alert("La fecha final no puede ser menor a la fecha actual.");
                return;
            }
      
            if (filter.initialDate > filter.finalDate) {
                alert("La fecha inicial no puede ser mayor que la fecha final.");
                return;
            }
      
            await fetchAvailableAppointments(filter);
            });
            filtroBtnInitialized = true;
        }});
      
   
        closeAppointmentBtn.addEventListener('click', (event) => {
            event.preventDefault();
            containerAppointment.style.display = 'none';
        
            // Limpiar tabla
            const tbody = document.querySelector('#appointmentsTable tbody');
            tbody.innerHTML = '';
        
            // Ocultar thead
            appointmentsTableHead.style.visibility = 'hidden';
            appointmentsTableHead.style.position = 'absolute';
        
            // Limpiar filtros
            document.getElementById('doctorSelect').value = '';
            document.getElementById('initialDate').value = '';
            document.getElementById('finalDate').value = '';
        
            // Limpiar botones de fechas
            document.getElementById('dateFilter').innerHTML = '';
        });
    
        


    async function fetchAvailableAppointments(filter) {
        try {
            const response = await fetch(`${API}/filtrar`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    runPatient: getCurrentRunPatient(),
                    idUser: filter.idUser,
                    initialDate: filter.initialDate,
                    finalDate: filter.finalDate
                })
            });
        
            if (!response.ok) {
                throw new Error('Error en la respuesta del servidor');
            }
        
            const appointments = await response.json();
            const appointmentsTableHead = document.getElementById('appointmentsTableHead');
            appointmentsTableHead.style.visibility = 'visible';
            appointmentsTableHead.style.position = 'relative';

                loadAppointments(appointments);
        } catch (error) {
            console.error('Error al obtener citas:', error);
        }
    }}

    function getDoctorNameById(id) {
        const select = document.getElementById('doctorSelect');
        const option = [...select.options].find(opt => opt.value === String(id));
        return option ? option.textContent : `Médico ID ${id}`;
    }


    function createTableAppointments(appointments) {
        const tableBody = document.querySelector("#appointmentsTable tbody");
        tableBody.innerHTML = ''; 
    
        if (!appointments || appointments.length === 0) {
            const row = document.createElement('tr');
            const cell = document.createElement('td');
            cell.colSpan = 5;
            cell.textContent = 'No hay citas disponibles.';
            row.appendChild(cell);
            tableBody.appendChild(row);
            return;
        }
    
        appointments.forEach(app => {
            const row = document.createElement('tr');
    
            const fechaHora = new Date(app.fechaHora);
            const fecha = fechaHora.toLocaleDateString('es-CL');
            const hora = fechaHora.toLocaleTimeString('es-CL', { hour: '2-digit', minute: '2-digit' });
    
            const doctorCell = document.createElement('td');
            doctorCell.textContent = getDoctorNameById(app.idUser);
            row.appendChild(doctorCell);
    
            const desdeCell = document.createElement('td');
            desdeCell.textContent = fecha;
            row.appendChild(desdeCell);
    
    
            const horaCell = document.createElement('td');
            horaCell.textContent = hora;
            row.appendChild(horaCell);
    
            const actionCell = document.createElement('td');
            const btn = document.createElement('button');
            btn.textContent = 'Agendar';
            btn.classList.add('btn', 'btn-sm', 'btn-success', 'w-100');
            btn.innerHTML = '<i class="fas fa-calendar-plus me-1"></i> Agendar';
            btn.onclick = async () => {
                const confirmacion = confirm(`¿Confirmas agendar una cita con ${getDoctorNameById(app.idUser)} el ${fecha} a las ${hora}?`);
            
                if (!confirmacion) return;

                try {
                    const response = await fetch(`${API}/post-appointment`, {
                      method: 'POST',
                      headers: {'Content-Type': 'application/json'},
                      body: JSON.stringify({
                        runPatient: getCurrentRunPatient(),         
                        idDoctor: app.idUser,           
                        dateAppointment: app.fechaHora 
                      })
                    });
                
                    if (!response.ok) {
                      const err = await response.json();
                      throw new Error(err.message || 'No se pudo agendar la cita');
                    }
                
                    const result = await response.json();
                    alert(`Cita creada con ID ${result.appointmentId}`);
                    closeAppointmentBtn.click();

                  } catch (error) {
                    console.error('Error al agendar cita:', error);
                    alert('Error al agendar la cita. Intenta nuevamente.');
                  }
                };
            actionCell.appendChild(btn);
            row.appendChild(actionCell);
    
            tableBody.appendChild(row);
        });
    }


    function loadAppointments(appointments) {
        const grouped = groupAppointmentsByDate(appointments);
        renderDateFilters(grouped);
      
        // Mostrar el primer día por defecto
        const keys = Object.keys(grouped).sort();
        if (keys.length) {
          createTableAppointments(grouped[keys[0]]);
        }
      }

    function groupAppointmentsByDate(appointments) {
        const grouped = {};
      
        appointments.forEach(app => {
          const [datePart] = app.fechaHora.split('T');       
          const [year, month, day] = datePart.split('-').map(Number);
      
          const key = `${year}-${String(month).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
      
          if (!grouped[key]) grouped[key] = [];
          grouped[key].push(app);
        });
      
        return grouped;
      }
      
    
        
      function renderDateFilters(groupedAppointments) {
        const container = document.getElementById('dateFilter');
        container.innerHTML = '';

        const dates = Object.keys(groupedAppointments).sort();
      
        dates.forEach(key => {
          const [y, m, d] = key.split('-').map(Number);
          const dt = new Date(y, m - 1, d); 
      
          const btn = document.createElement('button');
          btn.classList.add('btn', 'btn-outline-primary', 'btn-sm', 'me-2', 'mb-2');
      
          // formatea: "lun 20 abr"
          btn.textContent = dt.toLocaleDateString('es-CL', {
            weekday: 'short', day: 'numeric', month: 'short'
          });
          btn.onclick = () => createTableAppointments(groupedAppointments[key]);
      
          container.appendChild(btn);
        });
      }

    function isPastDate(fechaStr) {
        const fechaSeleccionada = new Date(fechaStr);
        const hoy = new Date();
        hoy.setHours(0, 0, 0, 0);
        return fechaSeleccionada < hoy;
    }

    // Función para Ver todas las citas del paciente
    async function fetchPatientAppointments(run) {
        try {
            const response = await fetch(`${API}/appointments/${run}`);
            if (!response.ok) {
                throw new Error('Error en la respuesta de la API');
            }
    
            const appointments = await response.json();
            const allAppointmentsTableHead = document.getElementById('allAppointmentsTableHead');
            allAppointmentsTableHead.style.visibility = 'visible';
            allAppointmentsTableHead.style.position = 'relative';
    
            createPatientAppointmentsTable(appointments);
            attachDeleteListener(); 
        } catch (error) {
            console.error('Error al obtener citas:', error);
        }
    }
    

    // Función para cerrar el contenedor de citas
    closeAllPatientAppointmentBtn.addEventListener('click', (event) => {
        event.preventDefault();
        const containerAllAppointments = document.getElementById('containerAllAppointments');
        containerAllAppointments.style.display = 'none';
        
         // Limpiar tabla
        const tbody = document.querySelector('#allAppointmentsTable tbody');
        tbody.innerHTML = '';
        
        // Ocultar thead
        allAppointmentsTableHead.style.visibility = 'hidden';
        allAppointmentsTableHead.style.position = 'absolute';
    });


    // Función para crear la tabla de citas del paciente
    async function createPatientAppointmentsTable(appointments) {
        const appointmentTableBody = document.getElementById('allAppointmentsTableBody');
        appointmentTableBody.innerHTML = ''; 

        appointments.forEach((appointment) => {
            const row = document.createElement('tr');
    
            row.innerHTML = `
                <td>${appointment.patientFullName}</td>
                <td>${appointment.doctorFullName}</td>
                <td>${appointment.dateAppointment}</td>
                <td>${appointment.dateTimeAppointment}</td>
                <td>
                    <button 
                        class="btn btn-sm btn-danger btn-delete-appointment1" 
                        data-id="${appointment.idAppointment}">
                        <i class="fas fa-trash-alt"></i> Cancelar
                    </button>
                </td>
            `;
    
            appointmentTableBody.appendChild(row);
        });
    }

    // Función para cancelar una cita
    let listenerAlreadyAdded = false; 

    function attachDeleteListener() {
        const appointmentTable = document.getElementById('allAppointmentsTable');
    
        if (listenerAlreadyAdded) return; 
    
        appointmentTable.addEventListener('click', async function(event) {
            const deleteAppointmentBtn1 = event.target.closest('.btn-delete-appointment1');
            if (deleteAppointmentBtn1) {
                const appointmentId = deleteAppointmentBtn1.dataset.id;
                await deleteAppointment1(appointmentId);
            }
        });
    
        listenerAlreadyAdded = true; 
    }
    

    
    let isDeletingAppointmentTable = false;

    async function deleteAppointment1(appointmentId) {
        if (isDeletingAppointmentTable) return;
    
        if (!confirm('¿Estás seguro de que deseas cancelar esta cita?')) {
            return; 
        }
    
        isDeletingAppointmentTable = true;
    
        try {
            const response = await fetch(`${API}/delete-appointment/${appointmentId}`, {
                method: 'DELETE'
            });
    
            if (!response.ok) {
                throw new Error('Error al cancelar la cita');
            }
    
            alert('Cita cancelada con éxito.');
            await fetchPatientAppointments(getCurrentRunPatient());
        } catch (error) {
            alert('Error al cancelar la cita: ' + error.message);
        } finally {
            isDeletingAppointmentTable = false;
        }
    }
    
    

    const fetchAppointmentsOfThewDayBtn = document.getElementById('fetchAppointmentsOfThewDayBtn');
    fetchAppointmentsOfThewDayBtn.addEventListener('click', async () => {
        closeCreatePatientBtn.click();
        closeUpdatePatientBtn.click();
        closeAllPatientAppointmentBtn.click();
        closePaymentBtn.click();
        closeAppointmentBtn.click();
        containerTablePatient.style.display = 'none';
        await fetchAppointmentsToday();
    });

    // Obtener Citas del Día
    async function fetchAppointmentsToday(){
        try {
            const response = await fetch(`${API}/appointments-today`);
            if (!response.ok) {
                throw new Error('Error en la respuesta de la API');
            }
    
            const appointments = await response.json();
            createTableAppointmentsOfTheDay(appointments);
        } catch (error) {
            console.error('Error al obtener citas:', error);
        }
    }

    // Crear Tabla de Citas del Día
    async function createTableAppointmentsOfTheDay(appointments) {
        const containerAppointmentsOfTheDay = document.getElementById('containerAppointmentsOfTheDay');
        containerAppointmentsOfTheDay.style.display = 'block';

        const AppointmentsOfTheDayTableHead = document.getElementById('AppointmentsOfTheDayTableHead');
        AppointmentsOfTheDayTableHead.style.visibility = 'visible';
        AppointmentsOfTheDayTableHead.style.position = 'relative';

        const AppointmentsOfTheDayTableBody = document.getElementById('AppointmentsOfTheDayTableBody');
        AppointmentsOfTheDayTableBody.innerHTML = ''; 

    
        appointments.forEach((appointment) => {
            const row = document.createElement('tr');
    
            row.innerHTML = `
                <td>${appointment.runPatient}</td>
                <td>${appointment.fullNamePatient}</td>
                <td>${appointment.fullNameDoctor}</td>
                <td>${appointment.dateAppointment}</td>
                <td>
                    <button 
                        class="btn btn-sm btn-success btn-pay-appointment" 
                        data-id="${appointment.idAppointment}">
                        <i class="fas fa-dollar-sign"></i> Pagar
                    </button>
                    <button 
                        class="btn btn-sm btn-danger btn-delete-appointment2" 
                        data-id="${appointment.idAppointment}">
                        <i class="fas fa-trash-alt"></i> Cancelar
                    </button>
                </td>
            `;
    
            AppointmentsOfTheDayTableBody.appendChild(row);
        });
    }
    
    document.addEventListener('click', function(event) {
        const btnPayAppointment = event.target.closest('.btn-pay-appointment');
        if (btnPayAppointment) {
            const idAppointment = btnPayAppointment.dataset.id;
            payAppointment(idAppointment);
        }

        const deleteAppointmentBtn2 = event.target.closest('.btn-delete-appointment2');
        if (deleteAppointmentBtn2) {
            const idAppointment = deleteAppointmentBtn2.dataset.id;
            handleDeleteAppointment(idAppointment);
        }
    });

    async function handleDeleteAppointment(idAppointment) {
        deleteAppointment2(idAppointment);
    }

    if (typeof window.paymentConfirmed === 'undefined') {
        window.paymentConfirmed = false;
    }

    
    async function payAppointment(idAppointment) {
        const receipt = document.getElementById('receipt');
        closeAppointmentBtn.click(); 
        closeUpdatePatientBtn.click(); 
        closeAllPatientAppointmentBtn.click();
        formCreatePatient.style.display = 'none';
        receipt.style.display = 'block';
        consultaAPI(idAppointment);
    }

    async function consultaAPI(idAppointment) {
        try {
            const response = await fetch(`${API}/math-of-appointment-payment/${idAppointment}`, {
                method: 'GET'
            });
    
            if (!response.ok) {
                throw new Error('Error al pagar la cita');
            }
    
            
            const paymentData = await response.json();
            fillReceipt(paymentData);


        } catch (error) {
            alert('Error al pagar la cita: ' + error.message);
        }
    }

    async function fillReceipt(paymentData) {
        const originalPrice = parseFloat(paymentData.priceAppointment);
        const discountPercentage = parseFloat(paymentData.discountAppointment);
    
        // Calcular el monto del descuento
        const discountAmount = originalPrice * discountPercentage;
    
        // Calcular el total a pagar (por si necesitas verificarlo)
        const totalPrice = originalPrice - discountAmount;
    
        // Mostrar datos en el HTML
        document.getElementById("appointmentId").textContent = paymentData.idAppointment;
        document.getElementById("originalPrice").textContent = "$" + originalPrice.toFixed(0);
        document.getElementById("discountAmount").textContent = "- $" + discountAmount.toFixed(0);
        document.getElementById("discount").textContent = (discountPercentage * 100).toFixed(0) + "%";
        document.getElementById("totalPrice").textContent = totalPrice.toFixed(0);
    
        // Input del monto pagado
        const amountInput = document.getElementById("AmountOfChange");

        // Evento para actualizar el vuelto en tiempo real
        amountInput.addEventListener('input', () => {
            const amountPaid = parseFloat(amountInput.value);
            if (!isNaN(amountPaid) && amountPaid >= totalPrice) {
                const change = amountPaid - totalPrice;
                document.getElementById("change").textContent = change.toFixed(0);
            } else {
                document.getElementById("change").textContent = "0.00";
            }
            console.log("Monto pagado:", amountPaid, "Cambio:", document.getElementById("change").textContent);  // Verificar los valores
        });
}

    // Control de flujo de pago
    let isPaymentInProgress = false;

    document.getElementById('payBtn').addEventListener('click', function(event) {
        event.preventDefault(); 

        if (isPaymentInProgress) return;  // Evitar hacer clic mientras el pago está en progreso
        
        const amountPaid = parseFloat(document.getElementById("AmountOfChange").value);
        const idAppointment = document.getElementById("appointmentId").textContent;
        const totalPrice = parseFloat(document.getElementById("totalPrice").textContent);

        // Verificar si el campo de monto está vacío o no es un número válido
        if (!amountPaid || isNaN(amountPaid) || amountPaid < totalPrice) {
            alert("Por favor, ingresa un monto válido mayor o igual al total a pagar.");
            return;  
        }

        isPaymentInProgress = true;  // Marcar que el pago está en progreso

        confirmPayment(idAppointment)
            .finally(() => {
                isPaymentInProgress = false;  // Restablecer el estado una vez completado el pago
            });
    });
        
        

    // Función para confirmar el pago
    async function confirmPayment(idAppointment) {
        try {
            const response = await fetch(`${API}/confirm-payment/${idAppointment}`, {
                method: 'PUT'
            });

            if (!response.ok) {
                throw new Error('No se pudo confirmar el pago');
            }

            if (!window.paymentConfirmed) {
                alert("Pago confirmado correctamente");
                window.paymentConfirmed = true; // Marca que el pago ya fue confirmado
            }

            // Resetear los campos para la próxima cita
            document.getElementById("AmountOfChange").value = '';
            document.getElementById("change").textContent = "0.00";

            closePaymentBtn.click();
            await fetchAppointmentsToday();

        } catch (error) {
            alert('Error al confirmar el pago: ' + error.message);
        }
    }


    
    const closePaymentBtn = document.getElementById('closePaymentBtn');
    closePaymentBtn.addEventListener('click', (event) => {
        event.preventDefault();
        const receipt = document.getElementById('receipt');
        receipt.style.display = 'none'; 
        document.getElementById("AmountOfChange").value = "";  
        document.getElementById("change").textContent = "0.00";  
        window.paymentConfirmed = false; // Resetear el estado de pago confirmado
    });

    

    let isDeleting = false;

    async function deleteAppointment2(idAppointment) {
        if (isDeleting) return;
        if (confirm('¿Estás seguro de que deseas cancelar esta cita?')) {
            isDeleting = true;
            try {
                const response = await fetch(`${API}/delete-appointment/${idAppointment}`, {
                    method: 'DELETE'
                });
    
                if (!response.ok) {
                    const errorText = await response.text();
                    throw new Error(`Error del servidor: ${errorText}`);
                }
    
                alert('Cita cancelada con éxito.');
                await fetchAppointmentsToday();
            } catch (error) {
                alert('Error al cancelar la cita: ' + error.message);
            } finally {
                isDeleting = false;
            }
        }
    }
});


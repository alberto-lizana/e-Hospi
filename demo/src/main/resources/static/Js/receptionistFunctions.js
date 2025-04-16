const API = 'http://localhost:8080/api/recepcionista';
const API1 = 'http://localhost:8080/api/admin';

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

    // Abrir Ventana emergente de Crear Paciente
    openCreatePatient.addEventListener('click', () => {
        cleanForm('create');
        loadSexSelect('3');
        loadHealthInsuranceSelect('0');
        formCreatePatient.style.display = 'block';
    });

    // Enviar formulario de Crear Paciente
    createPatientForm.addEventListener('submit', (event) => {
        event.preventDefault();  // Prevenir el comportamiento por defecto de submit
        errorContainerPatient.innerHTML = ''; // Limpiar mensajes de error
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
            const response = await fetch(`${API1}/sexs`);
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
                    <!-- Gestión de Pacientes -->
                    <button class="btn btn-sm btn-primary" onclick="updatePatient('${patient.runPatient}')">
                        <i class="fas fa-edit"></i> Editar
                    </button>
                    <button class="btn btn-sm btn-danger" onclick="deletePatient('${patient.runPatient}')">
                        <i class="fas fa-trash-alt"></i> Borrar
                    </button>
                </td>
                <td>
                    <!-- Gestión de Citas -->
                    <button class="btn btn-sm btn-success" onclick="scheduleAppointment('${patient.runPatient}')">
                        <i class="fas fa-calendar-plus"></i> Agendar
                    </button>
                    <button class="btn btn-sm btn-info" onclick="fetchPatientAppointments('${patient.runPatient}')">
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



 // Mostrar formulario de actualización de paciente
window.updatePatient = async function(runPatient) {
    cleanForm('update');
    formUpdatePatient.style.display = 'block';
    await getIdSexAndIdHealthInsurance(runPatient);

    // Asignar el click directamente (elimina cualquier listener anterior)
    updatePatientBtn.onclick = async function(event) {
        event.preventDefault();
        await updatePatient1(runPatient);
    };
};

// Obtener Sexo y Previsión Médica del paciente (para cargar los selects con los valores actuales)
async function getIdSexAndIdHealthInsurance(runPatient) {
    try {
        const response = await fetch(`${API}/getSexAndHealthInsurance/${runPatient}`);
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
    async function updatePatient1(runPatient, bornDatePatient) {
        const runPatientActual = runPatient;

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

        await updatePatient2(runPatientActual, updatedPatient);
    }

    // Hacer el request PUT para actualizar el paciente
    async function updatePatient2(runPatientActual, updatedPatient) {
        try {
            const response = await fetch(`${API}/update-patient/${runPatientActual}`, {
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
    window.deletePatient = async function(runPatient) {
        if (confirm('¿Estás seguro de que deseas eliminar este paciente?')) {
            try {
                const response = await fetch(`${API}/${runPatient}`, {
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

    window.scheduleAppointment = async function(runPatient) {
    }

    window.fetchPatientAppointments = async function(runPatient) {
    }


});

    

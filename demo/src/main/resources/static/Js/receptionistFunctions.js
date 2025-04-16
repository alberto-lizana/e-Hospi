const API = 'http://localhost:8080/api/recepcionista';
const API1 = 'http://localhost:8080/api/admin';

document.addEventListener('DOMContentLoaded', () => { 
    const fetchPatientBtn = document.getElementById('fetchPatientBtn');
    const openCreatePatient = document.getElementById('openCreatePatient');
    const fetchPatientAppointmentsBtn = document.getElementById('fetchPatientAppointmentsBtn');

    const formCreatePatient = document.getElementById('formCreatePatient'); // DIV
    const createPatientForm = document.getElementById('createPatientForm'); // FORM

    const createPatientBtn = document.getElementById('createPatientBtn');
    const closeCreatePatientBtn = document.getElementById('closeCreatePatientBtn');

    const errorContainerPatient = document.getElementById('errorContainerPatient');


    // Abrir Ventana emergente de Crear Paciente
    openCreatePatient.addEventListener('click', () => {
        cleanCreatePatientForm();
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
        cleanCreatePatientForm();
    });
    
    // Limpiar el formulario de crear paciente
    function cleanCreatePatientForm() {
        createPatientForm.reset();
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

        console.log("Sexo seleccionado:", rawSex);
        console.log("Previsión médica seleccionada:", rawInsurance);
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

        const errors = validateForm(newPatient);
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
            cleanCreatePatientForm();
            formCreatePatient.style.display = 'none';
    
        } catch (error) {
            alert("Ocurrió un error al crear el paciente");
        }
    }
    


    // Validar Crear Paciente
    function validateForm(newPatient) {
        const errors = [];

        const runError = validateRun(newPatient.runPatient);
        if (runError) errors.push(runError);

        const firstnameError = validateFirstname(newPatient.firstnamePatient);
        if (firstnameError) errors.push(firstnameError);

        const lastname1Error = validateLastname1(newPatient.lastnamePatient1);
        if (lastname1Error) errors.push(lastname1Error);

        const emailError = validateEmail(newPatient.emailPatient);
        if (emailError) errors.push(emailError);

        const phoneError = validatePhone(newPatient.phonePatient);
        if (phoneError) errors.push(phoneError);

        const bornDateError = validateBornDate(newPatient.bornDatePatient);
        if (bornDateError) errors.push(bornDateError);

        const healthInsuranceError = validateHealthInsurance(newPatient.idHealthInsurancePatient);
        if (healthInsuranceError) errors.push(healthInsuranceError);

        const sexError = validateSex(newPatient.idSexPatient);
        if (sexError) errors.push(sexError);

        return errors;
    }


    // Valida Run 
    function validateRun(run) {
        const regex = /^[0-9]{7,8}-[0-9kK]{1}$/;
        if (!run) {
            return "El RUN es obligatorio";
        }
        if (!regex.test(run)) {
            return "El RUN no es válido";
        }
        return null;
    }

    // Valida Nombre
    function validateFirstname(firstname) {
        if (!firstname) {
            return "El nombre es obligatorio";
        }
        if (firstname.length < 2 || firstname.length > 50) {
            return "El nombre debe tener entre 2 y 50 caracteres";
        }
        return null;
    }

    // Valida Apellido Paterno
    function validateLastname1(lastname1) {
        if (!lastname1) {
            return "El primer apellido es obligatorio";
        }
        if (lastname1.length < 2 || lastname1.length > 50) {
            return "El primer apellido debe tener entre 2 y 50 caracteres";
        }
        return null;
    }

    // Valida Email
    function validateEmail(email) {
        const regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        if (!email) {
            return "El correo electrónico es obligatorio";
        }
        if (!regex.test(email)) {
            return "El correo electrónico no es válido";
        }
        return null;
    }

    // Valida Teléfono
    function validatePhone(phone) {
        const regex = /^\d{9}$/;
        if (!phone) {
            return "El teléfono no puede estar vacío";
        }
        if (phone.length !== 9) {
            return "El teléfono debe tener exactamente 9 caracteres";
        }
        if (!regex.test(phone)) {
            return "El teléfono debe contener solo números";
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
});

const API = 'http://localhost:8080/api/recepcionista';
const API1 = 'http://localhost:8080/api/admin';
const API2 = 'http://localhost:8080/api/doctor';
let allPatientsDoctor = []; 


// Objeto para almacenar el estado de la cita actual
const idAppointmentState = {
    currentIdAppointment: null
};

// Función para establecer el valor de la cita actual
function setCurrentIdAppointment(id) {
    idAppointmentState.currentIdAppointment = parseInt(id);
}

// Función para obtener el valor de la cita actual
function getCurrentIdAppointment() {
    return parseInt(idAppointmentState.currentIdAppointment);
}


// Objeto para almacenar el run del paciente
const runState = {
    currentRun: null
};

// Función para establecer el valor del run
function setCurrentRun(run) {
    runState.currentRun = run;
}

// Función para obtener el valor del run
function getCurrentRunAppointment() {
    return runState.currentRun;
}

document.addEventListener("DOMContentLoaded", function() {
    loadDoctorsSelect();
    const openListOfPatientsBtn = document.getElementById("openListOfPatientsBtn");
    const fetchPatientByRunByDoctorBtn = document.getElementById("fetchPatientByRunByDoctorBtn");

    const containerTableMedicalHistoryDoctor = document.getElementById('containerTableMedicalHistoryDoctor');
    const diagnosisTreatmentContainerDoctor = document.getElementById('diagnosisTreatmentContainerDoctor');
    const medicationContainerDoctor = document.getElementById('medicationContainerDoctor');

    const closeMedicalHistoryBtn = document.getElementById('closeMedicalHistoryBtn');
    
    const sendDiagnosisDoctorBtn = document.getElementById('sendDiagnosisDoctorBtn');
    const closeDiagnosisDoctorBtn = document.getElementById('closeDiagnosisDoctorBtn');

    const sendMedicationDoctorBtn = document.getElementById('sendMedicationDoctorBtn');
    const closeMedicationDoctorBtn = document.getElementById('closeMedicationDoctorBtn');





    openListOfPatientsBtn.addEventListener("click", function() {
        const patientTableBodyDoctor = document.getElementById('patientTableBodyDoctor');
        
        patientTableBodyDoctor.innerHTML = ''; 

        fetchListOfPatients();
        containerTablePatientByRunDoctor.style.display = 'none';

        doctorSelect.value = '0';
        doctorSelect.dispatchEvent(new Event('change'));

    });

    fetchPatientByRunByDoctorBtn.addEventListener("click", function() {
    
    });

    async function fetchListOfPatients() {
        try {
            const response = await fetch(`${API2}/ver-pacientes-con-citas-hoy`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            });
    
            if (!response.ok) {
                throw new Error('Error al obtener los pacientes');
            }
    
            const patients = await response.json();
            
            //Limpiar antes de cargar
            patientTableBodyDoctor.innerHTML = '';
            allPatientsDoctor = []; //Limpiar también la lista en memoria

            allPatientsDoctor = patients;

            const containerTablePatientDoctor = document.getElementById('containerTablePatientDoctor');
            containerTablePatientDoctor.style.display = 'block';

            createTablePatientDoctor(patients);
    
        } catch (error) {
            console.error('Hubo un error:', error);
        }
    }
    

    function createTablePatientDoctor(patients) {    
        patients.forEach((patient) => {
            const row = document.createElement('tr');
    
            row.innerHTML = `
                <td>${patient.runPatient}</td>
                <td>${patient.fullNamePatient}</td>
                <td>${patient.fullNameDoctor}</td>
                <td>
                    <button 
                        class="btn btn-sm btn-info btn-medical-history w-100" 
                        data-run="${patient.runPatient}">
                        <i class="fas fa-notes-medical"></i> Ver
                    </button>
                </td>
                <td>
                    <button 
                        class="btn btn-sm btn-warning btn-diagnosis-treatment w-100" 
                        data-id="${patient.idAppointment}">
                        <i class="fas fa-stethoscope"></i> Diagnóstico
                    </button>
                </td>
                <td>
                    <button 
                        class="btn btn-sm btn-secondary btn-prescribe w-100" 
                        data-id="${patient.idAppointment}">
                        <i class="fas fa-pills"></i> Prescribir
                    </button>
                </td>
                <td>
                    <button 
                        class="btn btn-sm btn-success btn-mark-attended w-100" 
                        data-id="${patient.idAppointment}">
                        <i class="fas fa-check-circle"></i> Atendido
                    </button>
                </td>
            `;
    
            patientTableBodyDoctor.appendChild(row);
        });
    }


        document.addEventListener('click', async function(event) {
        const medicalHistory = event.target.closest('.btn-medical-history');
        if (medicalHistory) {
            const runPatient = medicalHistory.dataset.run;    
            containerTableMedicalHistoryDoctor.style.display = 'block'; 
            closeMedicationDoctorBtn.click();
            closeDiagnosisDoctorBtn.click();
            setCurrentRun(runPatient);
            RequestMedicalHistory();
            
        }
    
        const diagnosisTreatment = event.target.closest('.btn-diagnosis-treatment');
        if (diagnosisTreatment) {
            const idAppointment = diagnosisTreatment.dataset.id;
            diagnosisTreatmentContainerDoctor.style.display = 'block';
            closeMedicationDoctorBtn.click();
            closeMedicalHistoryBtn.click();
            setCurrentIdAppointment(idAppointment);
            
        }
    
        const prescribe = event.target.closest('.btn-prescribe');
        if (prescribe) {
            const idAppointment = prescribe.dataset.id;
            medicationContainerDoctor.style.display = 'block';
            closeDiagnosisDoctorBtn.click();
            closeMedicalHistoryBtn.click();
            setCurrentIdAppointment(idAppointment);
        }
    
        const markAttended = event.target.closest('.btn-mark-attended');
        if (markAttended) {
            const idAppointment = markAttended.dataset.id;
            closeDiagnosisDoctorBtn.click();
            closeMedicationDoctorBtn.click();
            closeMedicalHistoryBtn.click();
            setCurrentIdAppointment(idAppointment);
            changeSeenAppointment(idAppointment);
        }
    });


    closeDiagnosisDoctorBtn.addEventListener('click', function() {
        event.preventDefault();
        diagnosisTreatmentContainerDoctor.style.display = 'none';
        diagnosisTreatmentFormDoctor.reset();
    });


    closeMedicationDoctorBtn.addEventListener('click', function() {
        event.preventDefault();
        medicationContainerDoctor.style.display = 'none';
        medicationFormDoctor.reset();
    });

    closeMedicalHistoryBtn.addEventListener('click', function() {
        event.preventDefault();
        containerTableMedicalHistoryDoctor.style.display = 'none';

    });

    sendDiagnosisDoctorBtn.addEventListener('click', function(event) {
        event.preventDefault();
        // Obtener el id de la cita actual
        const idAppointment = getCurrentIdAppointment(); 
       
        // Obtener los valores de los campos del formulario
        const diagnosisDoctor = document.getElementById('diagnosisDoctor').value;
        const treatmentDoctor = document.getElementById('treatmentDoctor').value;

        if (diagnosisDoctor.trim().length === 0 || treatmentDoctor.trim().length === 0) {
            alert("Por favor, completa todos los campos.");
            return;
        }
    
        if (diagnosisDoctor.length > 1000) {
            alert("El diagnóstico no puede tener más de 1000 caracteres.");
            return;
        }
    
        if (treatmentDoctor.length > 1000) {
            alert("El tratamiento no puede tener más de 1000 caracteres.");
            return;
        }
        
        const data = {
            idAppointment: idAppointment,
            diagnosis: diagnosisDoctor,
            treatment: treatmentDoctor
        };

        diagnosisAndTreatmentFill(data);
    });

    async function diagnosisAndTreatmentFill(data) {
        console.log(data);
        try {
            const response = await fetch(`${API2}/agregar-diagnostico-y-tratamiento`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data),
            });
    
            const result = await response.json();
            if (!response.ok) {
                throw new Error(result.message); 
            }
    
            alert(result.message); 
            diagnosisTreatmentContainerDoctor.style.display = 'none';
            diagnosisTreatmentFormDoctor.reset();
            
        } catch (error) {
            console.error('Hubo un error:', error);
            alert(`Hubo un error: ${error.message}`); 
        }
    }


    sendMedicationDoctorBtn.addEventListener('click', function(event) {
        event.preventDefault();
        const idAppointment = getCurrentIdAppointment(); 
        const medicationDoctor = document.getElementById('medicationDoctor').value;

        if (!idAppointment || medicationDoctor.trim().length === 0) {
            alert("Por favor, completa todos los campos.");
            return;
        }
        

        const data = {
            idAppointment: idAppointment,
            prescribedMedicationsAppointment: medicationDoctor
        };

        prescribeMedication(data);
    });


    async function prescribeMedication(data) {
        console.log(data);
        try {
            const response = await fetch(`${API2}/agregar-prescripcion-medica`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data),
            });
           
            const result = await response.json();
            if (!response.ok) {
                throw new Error(result.message); 
            }

            alert(result.message);
            medicationContainerDoctor.style.display = 'none';
            medicationFormDoctor.reset();

        }catch (error) {
            console.error('Hubo un error:', error);
            alert(`Hubo un error: ${error.message}`);      
        }
    }
    
    async function changeSeenAppointment(idAppointment) {
        try {
            const response = await fetch(`${API2}/marcar-cita-como-atendida/${idAppointment}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                }
            });
    
            if (!response.ok) {
                throw new Error('Error al marcar la cita como atendida');
            }
    
            const result = await response.json();
            alert(result.message); 
            doctorSelect.value = '0';
            doctorSelect.dispatchEvent(new Event('change'));
            fetchListOfPatients();


        } catch (error) {
            console.error('Hubo un error:', error);
        }
    }


    async function RequestMedicalHistory() {
        const runPatient = getCurrentRunAppointment();
        try {
            const response = await fetch(`${API2}/ver-historial-medico/${runPatient}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            });
    
            if (!response.ok) {
                throw new Error('Error al obtener el historial médico');
            }
    
            const medicalHistory = await response.json();
    
            createTableMedicalHistoryDoctor(medicalHistory);
    
        } catch (error) {
            console.error('Hubo un error:', error);
        }
    }
    
    async function createTableMedicalHistoryDoctor(medicalHistory){
        const medicalHistoryTableBodyDoctor = document.getElementById('medicalHistoryTableBodyDoctor');
        medicalHistoryTableBodyDoctor.innerHTML = ''; 
    
        medicalHistory.forEach((history) => {
            const row = document.createElement('tr');
    
            row.innerHTML = `
                <td>${history.dateAppointment}</td>
                <td>${history.diagnosis}</td>
                <td>${history.treatment}</td>
                <td>${history.medications}</td>
            `;
    
            medicalHistoryTableBodyDoctor.appendChild(row);
        });
    }


    fetchPatientByRunByDoctorBtn.addEventListener("click", async function() {
        containerTablePatientDoctor.style.display = 'none';
        const runInput = document.getElementById("fetchPatientByRunDoctor").value;
        if (runInput.trim() === "") {
            alert("Por favor, ingresa un RUN.");
            return;
        }
        
        setCurrentRun(runInput);
        const runPatient = getCurrentRunAppointment();
        const validationError = validateRun(runPatient);
        
        doctorSelect.value = '0';
        doctorSelect.dispatchEvent(new Event('change'));
    
        if (validationError) {
            const errorDiv = document.getElementById('errorDashboardDoctor');
            errorDiv.textContent = validationError;
            setTimeout(() => {
                errorDiv.textContent = '';
            }, 5000);
            return; 
        }
    
        fetchPatientByRun(runPatient);
    });

    // Valida Run 
    function validateRun(runPatient) {
        const regex = /^[0-9]{7,8}-[0-9kK]{1}$/;
        if (!runPatient) {
            return "El RUN es obligatorio";
        }
        if (!regex.test(runPatient)) {
            return "El RUN no es válido";
        }
        return null;
    }

    async function fetchPatientByRun(runPatient) {

        try {
            const response = await fetch(`${API2}/${runPatient}`);
            const data = await response.json();
    
            if (!response.ok) {
                throw new Error(data.message || 'Error en la respuesta de la API');
            }
    
            // Limpiar mensaje de error si había
            const errorDiv = document.getElementById('errorDashboardDoctor');
            errorDiv.textContent = '';
    
            // Verificar que data tenga la estructura correcta
            if (data.runPatient && data.fullNamePatient) {
                createTablePatientDoctorByRun(data);
                containerTablePatientByRunDoctor.style.display = 'block';

            } else {
                throw new Error('Datos del paciente no válidos');
            }
    
        } catch (error) {
            const errorDiv = document.getElementById('errorDashboardDoctor');
            errorDiv.textContent = error.message || "No se encontraron pacientes con ese RUN.";
            console.error('Error al obtener el paciente:', error);
    
            // Ocultar tabla si hay error
            containerTablePatientByRunDoctor.style.display = 'none';
    
            setTimeout(() => {
                errorDiv.textContent = '';
            }, 5000);
        }
    }
    
    function createTablePatientDoctorByRun(patient) {
        const tableBody = document.getElementById('patientTableBodyDoctorByRun');
        tableBody.innerHTML = '';  // Limpiar tabla antes de agregar nuevos datos
        
        // Verificar que los datos sean correctos
        if (!patient || !patient.runPatient || !patient.fullNamePatient) {
            console.error('Faltan datos para el paciente');
            return;
        }
    
        const row = document.createElement('tr');
        
        row.innerHTML = `
            <td>${patient.runPatient}</td>
            <td>${patient.fullNamePatient}</td>
            <td>
                <button 
                    class="btn btn-sm btn-info btn-medical-history w-100" 
                    data-run="${patient.runPatient}">
                    <i class="fas fa-notes-medical"></i> Ver
                </button>
            </td>
        `;
        
        tableBody.appendChild(row);
    } 

    // Function para Traer a todos los médicos a un select
    async function loadDoctorsSelect() {
        try {
            const response = await fetch(`${API1}/doctors`);
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

    const doctorSelect = document.getElementById('doctorSelect');

    doctorSelect.addEventListener('change', function() {
        const selectedDoctorId = parseInt(this.value);
    
        let filteredPatients;
        
        if (selectedDoctorId === 0) {
            // Mostrar todos si seleccionó "Todos los médicos"
            filteredPatients = allPatientsDoctor;
        } else {
            filteredPatients = allPatientsDoctor.filter(patient => patient.idDoctor === selectedDoctorId);
        }
    
        const patientTableBodyDoctor = document.getElementById('patientTableBodyDoctor');
        patientTableBodyDoctor.innerHTML = ''; // Limpiamos la tabla
    
        createTablePatientDoctor(filteredPatients);
    });
});
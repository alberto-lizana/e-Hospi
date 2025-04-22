const API = 'http://localhost:8080/api/recepcionista';
const API1 = 'http://localhost:8080/api/admin';
const API2 = 'http://localhost:8080/api/doctor';

document.addEventListener("DOMContentLoaded", function() {

    const openListOfPatientsBtn = document.getElementById("openListOfPatientsBtn");
    const fetchPatientByRunByDoctorBtn = document.getElementById("fetchPatientByRunByDoctorBtn");


    openListOfPatientsBtn.addEventListener("click", function() {
        const containerTablePatientDoctor = document.getElementById('containerTablePatientDoctor');
        const patientTableBodyDoctor = document.getElementById('patientTableBodyDoctor');
    
        patientTableBodyDoctor.innerHTML = ''; 
        containerTablePatientDoctor.style.display = 'block';

        fetchListOfPatients();

    });

    fetchPatientByRunByDoctorBtn.addEventListener("click", function() {
    
    });

    async function fetchListOfPatients() {
        try {
            const response = await fetch(`${API2}/bring-in-patients-who-have-appointments-today`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            });
    
            if (!response.ok) {
                throw new Error('Error al obtener los pacientes');
            }
    
            const patients = await response.json();
    
            // Ahora puedes pasar los datos a tu función para crear la tabla
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
                <td>
                    <button 
                        class="btn btn-sm btn-info btn-medical-history w-100" 
                        data-id="${patient.idPatient}">
                        <i class="fas fa-notes-medical"></i> Ver
                    </button>
                </td>
                <td>
                    <button 
                        class="btn btn-sm btn-warning btn-diagnosis-treatment w-100" 
                        data-id="${patient.idPatient}">
                        <i class="fas fa-stethoscope"></i> Diagnóstico
                    </button>
                </td>
                <td>
                    <button 
                        class="btn btn-sm btn-secondary btn-prescribe w-100" 
                        data-id="${patient.idPatient}">
                        <i class="fas fa-pills"></i> Prescribir
                    </button>
                </td>
                <td>
                    <button 
                        class="btn btn-sm btn-success btn-mark-attended w-100" 
                        data-id="${patient.idPatient}">
                        <i class="fas fa-check-circle"></i> Atendido
                    </button>
                </td>
            `;
    
            patientTableBodyDoctor.appendChild(row);
        });
    }

});


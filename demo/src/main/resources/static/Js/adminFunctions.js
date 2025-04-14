    
    const API = 'http://localhost:8080/api/admin';
    const userTableBody = document.getElementById('userTableBody');
    const ContainerTable = document.getElementById('containerTable');
    
    document.addEventListener('DOMContentLoaded', () => {
        // Aquí se definen los elementos del DOM que se van a utilizar para crear Usuario
        const openCreateUser = document.getElementById('openCreateUser');
        const createUserBtn = document.getElementById('createUserBtn');
        const closeCreateUser = document.getElementById('closeCreateUser');
        const formCreateUser = document.getElementById('formCreateUser');

        // Aquí se definen los elementos del DOM que se van a utilizar para buscar Usuario
        const fetchUserBtn = document.getElementById('fetchUserBtn');
        const fetchUsersBtn = document.getElementById('fetchUsersBtn');
    
        // Botones para  modificar usuario
        const sendUpdateUserBtn = document.getElementById('sendUpdateUserBtn');
        const closeUpdateUserBtn = document.getElementById('closeUpdateUserBtn');

        fetchUsersBtn.addEventListener('click', async () => {
            event.preventDefault();
            clearTable(); 
            fetchAllUsers();
        });

        fetchUserBtn.addEventListener('click', async () => {
            event.preventDefault(); // Evitar el envío del formulario por defecto
            const emailUser = document.getElementById('buscarPorEmailUsuario').value;
            clearTable(); 
            fetchUserByEmail(emailUser);
        });

        // Evento para abrir el formulario
        openCreateUser.addEventListener('click', () => {
            formCreateUser.style.display = 'block';
            loadRolesSelect('0');
            loadSexSelect('0');
            clearTable(); // Si tienes alguna tabla que limpiar, implementa la función
        });
    });


    // Al hacer clic en el botón de "Crear Usuario", validamos el formulario
    createUserBtn.addEventListener('click', async (event) => {
        event.preventDefault();

        // Obtener los valores del formulario
        const userDto = {
            runUser: document.getElementById('runUser').value,
            firstNameUser: document.getElementById('firstNameUser').value,
            lastNameUser1: document.getElementById('lastNameUser1').value,
            lastNameUser2: document.getElementById('lastNameUser2').value,
            emailUser: document.getElementById('emailUser').value,
            phoneUser: document.getElementById('phoneUser').value,
            passwordUser: document.getElementById('passwordUser').value,
            idRole: parseInt(document.getElementById('userRole0').value),
            idSex: parseInt(document.getElementById('userSex0').value)
        };

        // Validar el formulario
        const errors = validateForm(userDto);

        // Si hay errores, mostrar los mensajes de error
        if (errors.length > 0) {
            const errorContainer = document.getElementById('errorContainer');
            errorContainer.innerHTML = `<ul>${errors.map(error => `<li>${error}</li>`).join('')}</ul>`;
            return;
        }

        // Si no hay errores, continuar con la creación del usuario
        createUser(userDto);
    });

    // Evento para cerrar el formulario sin crear el usuario
    closeCreateUser.addEventListener('click', () => {
        formCreateUser.style.display = 'none';
        clearForm("create");
    });


    // Function para Cargar Los Roles de Crear Usuario
    async function loadRolesSelect (lugar, selectedId = null) {
        try {
            const response = await fetch(`${API}/roles`);
            if (!response.ok) {
                throw new Error('Error en la respuesta de la API');
            }

            const roles = await response.json();
            const select = document.getElementById('userRole' + lugar);

            select.innerHTML = '';

            roles.forEach((role) => {
                const option = document.createElement('option');
                option.value = role.idRole;
                option.textContent = role.nameRole;
                if (role.idRole === selectedId) {
                    option.selected = true;
                }
                select.appendChild(option);
            });
        } catch (error) {
            console.error('Error al obtener los roles:', error);
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
            console.error('Error al obtener los sexis:', error);
        }
    }


    // Función para crear el usuario
    async function createUser(userDto) {
        try {
            const response = await fetch(`${API}/create-user`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(userDto)
            });

            const data = await response.json();

            if (!response.ok) {
                if (response.status === 400 && data.errors) {
                    // Mostrar errores de validación
                    let errorMessage = '<ul>';
                    for (const [campo, error] of Object.entries(data.errors)) {
                        errorMessage += `<li><strong>${campo}:</strong> ${error}</li>`;
                    }
                    errorMessage += '</ul>';
                    
                    // Mostrar los errores en un contenedor
                    document.getElementById('errorContainer').innerHTML = errorMessage;
                } else {
                    alert(data.message || 'Error desconocido al crear el usuario');
                }
                return;
            }

            // Si todo es exitoso, mostramos el mensaje y limpiamos el formulario
            alert('Usuario creado exitosamente');
            clearForm("create");
            formCreateUser.style.display = 'none';

        } catch (error) {
            console.error("Error de red o excepción:", error);
            alert("Ocurrió un error al crear el usuario");
        }
    }


    // Function para Obtener Usuario por Email
    async function fetchUserByEmail(emailUser) {
        try {
            const response = await fetch(`${API}/${emailUser}`);
            if (!response.ok) {
                throw new Error('Error en la respuesta de la API');
            }

            const user = await response.json();
            console.log(user);
            createTable([user]); 
            
        } catch (error) {
            console.error('Error al obtener el usuario:', error);
        }
    }


    // Function para Obtener a Todos los Usuarios
    async function fetchAllUsers() {
        try {
            const response = await fetch(`${API}/users`);
            if (!response.ok) {
                throw new Error('Error en la respuesta de la API');
            }

            const users = await response.json();
            console.log(users);


            createTable(users); 


        } catch (error) {
            console.error('Error al obtener los usuarios:', error);
        }
    }


    // function para crear la tabla 
    function createTable(users) {
        userTableBody.innerHTML = ''; 
        ContainerTable.style.display = 'block'; 

        users.forEach((user) => {
            const row = document.createElement('tr');

            row.innerHTML = `
                <td>${user.runUser}</td>
                <td>${user.fullName}</td>
                <td>${user.emailUser}</td>
                <td>${user.phoneUser}</td>
                <td>${user.nameRole}</td>
                <td>${user.nameSex}</td>
                <td>
                    <button class="btn btn-sm btn-primary" onclick="updateUser('${user.runUser}')">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn btn-sm btn-danger" onclick="deleteUser('${user.runUser}')">
                        <i class="fas fa-trash-alt"></i>
                    </button>
                </td>
            `;

            userTableBody.appendChild(row);
    });
    }
    

    function clearTable() {
        userTableBody.innerHTML = ''; 
        ContainerTable.style.display = 'none'; 
    }

    // Function para Editar Usuario
    async function updateUser(runUser) {
        runUserActual = runUser;  // Asignamos el runUser actual

        const formUpdateUser = document.getElementById('formUpdateUser');
        formUpdateUser.style.display = 'block';

        let idSex = null;
        let idRole = null;
        let emailUser = null;

        try {
            const response = await fetch(`${API}/user/${runUserActual}`);
            if (!response.ok) {
                throw new Error('Error en la respuesta de la API');
            }

            const data = await response.json();
            idSex = data.idSex;
            idRole = data.idRole;
            emailUser = data.emailUser;

            // Cargar los valores en el formulario
            await loadSexSelect('1', idSex);
            await loadRolesSelect('1', idRole);

            document.getElementById('userRole1').value = idRole || '';
            document.getElementById('userSex1').value = idSex || '';
        } catch (error) {
            console.error('Error al obtener El rol y el sexo:', error);
        }
    }



    // Agregar el listener para el botón de actualización fuera de la función updateUser
    sendUpdateUserBtn.addEventListener('click', async (event) => {
        event.preventDefault();

        const updatedUser = {
            runUser: document.getElementById('updateRunUser').value.trim() || null,
            firstNameUser: document.getElementById('updateFirstNameUser').value.trim() || null,
            lastNameUser1: document.getElementById('updateLastNameUser1').value.trim() || null,
            lastNameUser2: document.getElementById('updateLastNameUser2').value.trim() || null,
            emailUser: document.getElementById('updateEmailUser').value.trim() || null,
            phoneUser: document.getElementById('updatePhoneUser').value.trim() || null,
            passwordUser: document.getElementById('updatePasswordUser').value.trim() || null,
            idRole: parseInt(document.getElementById('userRole1').value),
            idSex: parseInt(document.getElementById('userSex1').value)
        };

        try {
            const response = await fetch(`${API}/${runUserActual}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(updatedUser)
            });

            if (response.ok) {
                alert('Usuario actualizado correctamente');
                clearForm("update");
                formUpdateUser.style.display = 'none';
                fetchAllUsers();
            } else {
                const errorText = await response.text();
                alert('Error al actualizar usuario: ' + errorText);
            }

        } catch (error) {
            alert('Error en la petición: ' + error.message);
        }
    });



    // Evento para cerrar el formulario de actualización
    closeUpdateUserBtn.addEventListener('click', () => {
        formUpdateUser.style.display = 'none';
        clearForm("update");
    });



    
    // Function para Eliminar Usuario
    async function deleteUser(runUser){

    }


    // Función para limpiar el formulario
    function clearForm(type) {
    if (type === "create") {
        document.getElementById('creatreUserForm').reset();
        document.getElementById('errorContainer').innerHTML = '';  // Limpiar errores
    }
    if (type === "update") {
        document.getElementById('updateUserForm').reset();
        document.getElementById('errorContainer').innerHTML = '';  // Limpiar errores
    }
    }


    // Función para validar el RUN
    function validateRun(runUser) {
        const runRegex = /^[0-9]{7,8}-[0-9kK]$/;
        if (!runUser) {
            return "El RUN no puede ser nulo";
        }
        if (!runRegex.test(runUser)) {
            return "El RUN debe ser válido (ej: 6282112-7 o 13083789-4)";
        }
        return null;
    }

    // Función para validar el primer nombre
    function validateFirstName(firstNameUser) {
        if (!firstNameUser) {
            return "El primer nombre no puede estar vacío";
        }
        return null;
    }

    // Función para validar el apellido
    function validateLastName(lastNameUser1) {
        if (!lastNameUser1) {
            return "El apellido no puede estar vacío";
        }
        return null;
    }

    // Función para validar el email
    function validateEmail(emailUser) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailUser) {
            return "El email no puede estar vacío";
        }
        if (!emailRegex.test(emailUser)) {
            return "El email no es válido";
        }
        return null;
    }

    // Función para validar el teléfono
    function validatePhone(phoneUser) {
        const phoneRegex = /^\d{9}$/;
        if (!phoneUser) {
            return "El teléfono no puede estar vacío";
        }
        if (phoneUser.length !== 9) {
            return "El teléfono debe tener exactamente 9 caracteres";
        }
        if (!phoneRegex.test(phoneUser)) {
            return "El teléfono debe contener solo números";
        }
        return null;
    }

    // Función para validar la contraseña
    function validatePassword(passwordUser) {
        if (!passwordUser) {
            return "La contraseña no puede estar vacía";
        }
        return null;
    }

    // Función para validar el rol (debe ser obligatorio)
    function validateRole(idRole) {
        if (idRole === null || idRole === undefined) {
            return "El rol es obligatorio";
        }
        return null;
    }

    // Función para validar el sexo (debe ser obligatorio)
    function validateSex(idSex) {
        if (idSex === null || idSex === undefined) {
            return "El sexo es obligatorio";
        }
        return null;
    }

    // Función para validar todo el formulario
    function validateForm(form) {
        let errors = [];

        // Validaciones
        const runError = validateRun(form.runUser);
        if (runError) errors.push(runError);

        const firstNameError = validateFirstName(form.firstNameUser);
        if (firstNameError) errors.push(firstNameError);

        const lastNameError = validateLastName(form.lastNameUser1);
        if (lastNameError) errors.push(lastNameError);

        const emailError = validateEmail(form.emailUser);
        if (emailError) errors.push(emailError);

        const phoneError = validatePhone(form.phoneUser);
        if (phoneError) errors.push(phoneError);

        const passwordError = validatePassword(form.passwordUser);
        if (passwordError) errors.push(passwordError);

        const roleError = validateRole(form.idRole);
        if (roleError) errors.push(roleError);

        const sexError = validateSex(form.idSex);
        if (sexError) errors.push(sexError);

        return errors;
    }

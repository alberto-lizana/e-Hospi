    
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
    
        fetchUsersBtn.addEventListener('click', async () => {
            event.preventDefault();
            clearTable(); 
            fetchAllUsers();
        });

        fetchUserBtn.addEventListener('click', async () => {
            event.preventDefault(); // Evitar el envío del formulario por defecto
            const emailUser = document.getElementById('emailUser').value;

        });

        // Ahora sí: coloca los eventListeners dentro
        openCreateUser.addEventListener('click', () => {
            formCreateUser.style.display = 'block';
            loadRolesSelect();
            loadSexSelect();
            clearTable();
        });

        createUserBtn.addEventListener('click', async (event) => {
            event.preventDefault(); // Evitar el envío del formulario por defecto

            const newUser = {
                runUser: document.getElementById('runUser').value,
                firstNameUser: document.getElementById('firstNameUser').value,
                lastNameUser1: document.getElementById('lastNameUser1').value,
                lastNameUser2: document.getElementById('lastNameUser2').value,
                emailUser: document.getElementById('emailUser').value,
                phoneUser: document.getElementById('phoneUser').value,
                passwordUser: document.getElementById('passwordUser').value,
                idRole: parseInt(document.getElementById('userRole').value),
                idSex: parseInt(document.getElementById('userSex').value)
            };

            createUser(newUser);
        });
    
        closeCreateUser.addEventListener('click', () => {
            formCreateUser.style.display = 'none';
        });
    });


    // Function para Cargar Los Roles de Crear Usuario
    async function loadRolesSelect () {
        try {
            const response = await fetch(`${API}/roles`);
            if (!response.ok) {
                throw new Error('Error en la respuesta de la API');
            }

            const roles = await response.json();
            const select = document.getElementById('userRole');

            select.innerHTML = '';

            roles.forEach((role) => {
                const option = document.createElement('option');
                option.value = role.idRole;
                option.textContent = role.nameRole;
                select.appendChild(option);
            });
        } catch (error) {
            console.error('Error al obtener los roles:', error);
        }
    }

    // Function para Cargar Los Sexos de Crear Usuario
    async function loadSexSelect () {
        try {
            const response = await fetch(`${API}/sexs`);
            if (!response.ok) {
                throw new Error('Error en la respuesta de la API');
            }

            const sexs = await response.json();
            const select = document.getElementById('userSex');

            select.innerHTML = '';

            sexs.forEach((sex) => {
                const option = document.createElement('option');
                option.value = sex.idSex;
                option.textContent = sex.nameSex;
                select.appendChild(option);
            });
        } catch (error) {
            console.error('Error al obtener los sexis:', error);
        }
    }


    async function createUser(newUser){
        try {
            const response = await fetch(`${API}/create-user`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(newUser)
            });

            if (!response.ok) {
                console.log("Nuevo usuario:", newUser);

                throw new Error('Error al crear el usuario');
            }

            const data = await response.json();
            console.error('Usuario creado exitosamente', data);

        } catch (error) {
            console.log("Nuevo usuario:", newUser);

            console.error('Error al crear el usuario:', error);
        }
    }




    // Function para Obtener Usuario por Email


    // Function para Obtener a Todos los Usuarios

    async function fetchAllUsers() {
        try {
            const response = await fetch(`${API}/users`);
            if (!response.ok) {
                throw new Error('Error en la respuesta de la API');
            }

            const users = await response.json();
            console.log(users);

            document.getElementById('containerTable').style.display = 'block';
            const userTableBody = document.getElementById('userTableBody');
            userTableBody.innerHTML = ''; 

            users.forEach((user) => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${user.runUser}</td>
                    <td>${user.fullName}</td>
                    <td>${user.emailUser}</td>
                    <td>${user.phoneUser}</td>
                    <td>${user.nameRole}</td>
                    <td>${user.nameRole}</td>
                    <td>
                        <button class="btn btn-sm btn-primary" onclick="editUser('${user.runUser}')">
                            <i class="fas fa-edit"></i>
                        </button>
                        <button class="btn btn-sm btn-danger" onclick="deleteUser('${user.runUser}')">
                            <i class="fas fa-trash-alt"></i>
                        </button>
                    </td>
                `;
                userTableBody.appendChild(row);
            });

        } catch (error) {
            console.error('Error al obtener los usuarios:', error);
        }
    }
    function clearTable() {
        userTableBody.innerHTML = ''; 
        ContainerTable.style.display = 'none'; 
    }
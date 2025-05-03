USE egesven;
/*  Roles */  
INSERT INTO role(id_role, name_role) values (1, 'ADMINISTRADOR');
INSERT INTO role(id_role, name_role) values (2, 'RECEPCIONISTA');
INSERT INTO role(id_role, name_role) values (3, 'MEDICO');

/*  Sexos  */  
INSERT INTO sex(id_sex, name_sex) values (1, 'MASCULINO');
INSERT INTO sex(id_sex, name_sex) values (2, 'FEMENINO');

/*  Agregando Al Administrador  */  
INSERT INTO user  (id_user,email_user, first_name_user, last_name_user1, last_name_user2, password_user, phone_user, run_user, id_role, id_sex)
			values(0, 'admin@gmail.com', 'Alberto', 'Lizana', 'Rojas', '123456789', '955567025', '15088870-3', 1, 1);

/*  Agregando A Médico  */
INSERT INTO user  (id_user,email_user, first_name_user, last_name_user1, last_name_user2, password_user, phone_user, run_user, id_role, id_sex)
			values(1, 'matias.riquelme@example.com', 'Matías', 'Riquelme', 'Soto', 'claveSegura2025', '912345678', '87654321-K', 3, 1);
            
/*  Agregando A Recepcionista  */
INSERT INTO user  (id_user,email_user, first_name_user, last_name_user1, last_name_user2, password_user, phone_user, run_user, id_role, id_sex)
			values(2, 'sojas@gmail.com', 'Martin', 'Soto', 'Rojas', '123456789', '956967028', '19078984-5', 2, 1);

/*  Agregando A Médico  */
INSERT INTO user  (id_user,email_user, first_name_user, last_name_user1, last_name_user2, password_user, phone_user, run_user, id_role, id_sex)
			values(3, 'locopepe@gmail.com', 'pepe', 'locaso', 'locateli', '147896321481218152', '954787914', '19087789-k', 3, 1);    

/*  Agregando A Adminitrador  */
INSERT INTO user  (id_user,email_user, first_name_user, last_name_user1, last_name_user2, password_user, phone_user, run_user, id_role, id_sex)
			values(4, 'admin2@gmail.com', 'Andres', 'Olea', 'Peña', '123456789', '978457815', '18784985-4', 1, 1);   

/*  Agregando A Médicos  */            
INSERT INTO user  (id_user,email_user, first_name_user, last_name_user1, last_name_user2, password_user, phone_user, run_user, id_role, id_sex)
			values(5, 'felolje@gmail.com', 'Felipe', 'Olea', 'Jerez', '159789456123', '458159784', '19084784-4', 3, 1);   
		
INSERT INTO user  (id_user,email_user, first_name_user, last_name_user1, last_name_user2, password_user, phone_user, run_user, id_role, id_sex)
			values(6, 'Boom@gmail.com', 'Isaias', 'aham', 'Boom', '123456789159753', '978456871', '12345795-7', 3, 1);   

/*  Agregando A Recepcionistas  */
INSERT INTO user  (id_user,email_user, first_name_user, last_name_user1, last_name_user2, password_user, phone_user, run_user, id_role, id_sex)
			values(7, 'juan.perez@email.com', 'Juan', 'Pérez', 'Lopez', 'password123', '123456789', '12345678-9', 2, 1);   

INSERT INTO user  (id_user,email_user, first_name_user, last_name_user1, last_name_user2, password_user, phone_user, run_user, id_role, id_sex)
			values(8, 'CastiCas@Gmail.com', 'Antonia', 'Castillo', 'Castro', '123456789', '945678987', '78985564-4', 2, 2);   
            
/*  Agregando Horarios Fijos médicos  */ 
INSERT INTO time_slot (start_time, end_time, id_user)
VALUES
    ('08:00:00', '15:00:00', 1),
    ('15:00:00', '21:00:00', 3),
    ('08:00:00', '15:00:00', 5),
    ('15:00:00', '21:00:00', 6);
            
/*  Agregando Previsiones médicas  */  
INSERT INTO health_insurance VALUES
(1, 'FONASA'),
(2, 'COLMENA'),
(3, 'CRUZ BLANCA'),
(4, 'CONSALUD'),
(5, 'BANMÉDICA'),
(6, 'VIDA TRES'),
(7, 'NUEVA MASVIDA');

/*  Consultas Completas de Tablas  */     
SELECT * FROM user;
SELECT * FROM time_slot;
SELECT * FROM role;
SELECT * FROM sex; 
SELECT * FROM patient;
SELECT * FROM health_insurance;
SELECT * FROM appointment; 
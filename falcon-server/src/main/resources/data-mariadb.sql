INSERT INTO course (id, course_description,course_name) VALUES (1, 'Aulas de música e canto.','Música'),(2, 'Aulas de natação e mergulho.','Natação'),(3, 'Aulas de costura e pintura.','Ateliê e costura');

INSERT INTO student (id, cpf,date_of_birth,date_of_registry,email,first_name,last_name,rg,status) VALUES (1, '206.879.520-50','1997-12-20','2019-04-20','victorryan@bemarius.com.br','Victor','Ryan Enrico Pereira','42.524.209-2', 'ACTIVE'),(2, '866.758.912-86','1995-01-01','2019-02-02','tomasruan@cernizza.com.br','Tomás','Ruan Paulo Nunes','29.338.337-6', 'INACTIVE'),(3, '791.579.145-16','1991-04-01','2019-02-04','isadoralavinia@willianfernandes.com.br','Isadora','Lavínia Priscila Viana','15.625.318-5', 'PENDING_TRANSFER');

INSERT INTO courses_student (course_id,student_id) VALUES (1,1),(1,2),(2,2),(3,1),(3,2),(3,3);

INSERT INTO person_responsible (id, first_name,last_name,cpf,rg,date_of_birth,email) VALUES (1, 'Mateus','Eduardo Breno Moura','331.536.130-44','38.354.981-4','1981-05-05','mateuseduardobrenomoura-91@redacaofinal.com.br'),(2, 'Sabrina','Mariah Isabelly Assis','211.053.600-40','13.777.713-9','1963-08-02','sabrina.mariah@example.com'),(3, 'Sônia','Vera da Paz','275.484.490-20','11.195.462-9','1963-10-01','sonia.vera@example.com'),(4, 'Lorenzo','Nicolas Daniel Nunes','344.894.657-50','19.342.358-3','1969-09-08','lorenzo.nicolas@example.con'),(5, 'Bryan','Benedito Galvão','159.193.002-21','47.333.277-2','1969-08-10','bryan.benedito@example.com'),(6, 'Mário','Marcelo Monteiro','088.373.202-56','11.232.733-3','1969-04-04','mario.marcelo@example.com');

INSERT INTO person_responsible_student (person_responsible_id,student_id) VALUES (1,1),(2,1),(3,2),(4,2),(5,3),(6,3);

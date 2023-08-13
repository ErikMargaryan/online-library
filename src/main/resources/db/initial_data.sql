INSERT INTO library.public.roles (id, description, name)
VALUES (1, 'User role', 'USER'),
       (2, 'Admin role', 'ADMIN'),
       (3, 'Super Admin role', 'SUPER_ADMIN')
ON CONFLICT (id) DO NOTHING;

-- password123
-- adminpass
-- superpass
INSERT INTO library.public.users (id, email, first_name, last_name, password, phone)
VALUES (1, 'user@example.com', 'User', 'Margaryan', '$2a$12$1XQKMVp.gyi9gktxaT8cSOsSityoRZMBPQUB7SG7cuDiFcX.GGj62', '123-456-7890'),
       (2, 'admin@example.com', 'Admin', 'User', '$2a$12$c/70brVXcRtC.gvNA0cnpujlYs0l1uHpWURAo2Pe6vpcy6rYoL2Vm', '987-654-3210'),
       (3, 'erickmargarian@gmail.com', 'Erik', 'Margaryan', '$2a$12$OWX8XB7k29peM5MYywRw7e56ucHw5joBrmC.WzjYIroJb8SDVF1ea', '+37498490961')
ON CONFLICT (id) DO NOTHING;

INSERT INTO library.public.users_roles (role_id, user_id)
VALUES (1, 1),
       (2, 2),
       (3, 3)
ON CONFLICT DO NOTHING;

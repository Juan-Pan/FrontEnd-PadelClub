merge into rol (id_rol, nombre_rol, descripcion) key (id_rol)
values (1, 'USER', 'Usuario estándar');

merge into rol (id_rol, nombre_rol, descripcion) key (id_rol)
values (2, 'ADMIN', 'Administrador');

merge into usuario
    (nombre, apellidos, email, password, telefono, activo, fecha_registro, id_rol)
key (email)
values
    ('Carlos', 'Alcaraz', 'carlos@padel.com', '{noop}Password123!', '600123456', true, NOW(), 1);

merge into usuario
    (nombre, apellidos, email, password, telefono, activo, fecha_registro, id_rol)
key (email)
values
    ('Ana', 'Admin', 'ana.admin@padel.com', '{noop}123456', '600000001', true, NOW(), 2);

merge into usuario
    (nombre, apellidos, email, password, telefono, activo, fecha_registro, id_rol)
key (email)
values
    ('Usuario', 'Prueba', 'user@padel.com', '{noop}1234', '600000002', true, NOW(), 1);

merge into usuario
    (nombre, apellidos, email, password, telefono, activo, fecha_registro, id_rol)
key (email)
values
    ('Admin', 'Prueba', 'admin@padel.com', '{noop}1234', '600000003', true, NOW(), 2);

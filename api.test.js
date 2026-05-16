const { apiFetch } = require('./api');

// Simulamos la función fetch nativa del navegador
global.fetch = jest.fn();

beforeEach(() => {
    fetch.mockClear();
    localStorage.clear();
});

describe('Pruebas de api.js', () => {

    test('Authorization header se incluye si hay token', async () => {
        // Simulamos que el usuario tiene sesión
        localStorage.setItem('userAuth', 'dXNlcjoxMjM0');

        // Simulamos una respuesta exitosa del servidor
        fetch.mockResolvedValue({
            ok: true,
            status: 200,
            json: async () => ({ mensaje: 'ok' })
        });

        await apiFetch('/test-endpoint');

        // Verificamos que fetch fue llamado con la cabecera correcta
        expect(fetch).toHaveBeenCalledTimes(1);
        const opcionesLlamada = fetch.mock.calls[0][1];
        expect(opcionesLlamada.headers['Authorization']).toBe('Basic dXNlcjoxMjM0');
    });

    test('mensaje de error correcto en 401', async () => {
        // Simulamos una respuesta de credenciales inválidas
        fetch.mockResolvedValue({
            ok: false,
            status: 401
        });

        // Verificamos que la promesa es rechazada con el mensaje esperado
        await expect(apiFetch('/test-endpoint')).rejects.toThrow('No autorizado. Email o contraseña incorrectos.');
    });

});
const AuthService = require('./auth');

// Simulamos la función global apiFetch de la que depende auth.js
global.apiFetch = jest.fn();

beforeEach(() => {
    localStorage.clear();
    jest.clearAllMocks();

    // Simulamos la redirección del navegador para poder testearla
    delete window.location;
    window.location = { replace: jest.fn() };
});

describe('Pruebas de AuthService', () => {

    test('isLoggedIn devuelve false si no hay token', () => {
        expect(AuthService.isLoggedIn()).toBe(false);
    });

    test('isLoggedIn devuelve true si hay token guardado', () => {
        localStorage.setItem('userAuth', 'dXNlcjoxMjM0');
        expect(AuthService.isLoggedIn()).toBe(true);
    });

    test('logout limpia localStorage y redirige al index', async () => {
        // Estado inicial: Usuario logueado
        localStorage.setItem('userAuth', 'token123');
        localStorage.setItem('userInfo', JSON.stringify({ nombre: 'Test' }));

        // Simulamos que el cierre de sesión en backend funciona bien
        global.apiFetch.mockResolvedValue(null);

        // Ejecutamos logout
        await AuthService.logout();

        // Comprobaciones
        expect(localStorage.getItem('userAuth')).toBeNull();
        expect(localStorage.getItem('userInfo')).toBeNull();
        expect(window.location.replace).toHaveBeenCalledWith('index.html');
    });

});
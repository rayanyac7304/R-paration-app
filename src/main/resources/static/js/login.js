function toggleFields() {
    const role = document.getElementById('role').value;
    const loginGroup = document.getElementById('login-group');
    const passwordGroup = document.getElementById('password-group');
    const codeGroup = document.getElementById('code-group');

    if (role === 'CLIENT') {
        loginGroup.style.display = 'none';
        passwordGroup.style.display = 'none';
        codeGroup.style.display = 'block';
    } else if (role === 'ADMIN' || role === 'REPARATEUR') {
        loginGroup.style.display = 'block';
        passwordGroup.style.display = 'block';
        codeGroup.style.display = 'none';
    } else {
        loginGroup.style.display = 'none';
        passwordGroup.style.display = 'none';
        codeGroup.style.display = 'none';
    }
}

document.addEventListener('DOMContentLoaded', toggleFields);

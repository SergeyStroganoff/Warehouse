const ModalActionClass = class {
    // тело класса
    constructor() {
    }
    roleElement = document.querySelector(".error-status");
    init() {
        alert("The button was clicked!");
        if (this.roleElement.getAttribute('value') === 'error') {
            let myModal = new bootstrap.Modal(document.querySelector('#resultModalMenu'));
            myModal.show();
        }
    }
}

myApp = new ModalActionClass;
myApp.init();

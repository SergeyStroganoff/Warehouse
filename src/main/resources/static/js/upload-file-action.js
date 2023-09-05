// JavaScript code for handling file uploads
const dropArea = document.getElementById('drop-area');
const fileInput = document.getElementById('file-input');
const fileList = document.getElementById('file-list');
const uploadButton = document.getElementById('upload-button');

dropArea.addEventListener("click", (e) => {
    fileInput.click();
});

dropArea.addEventListener('dragenter', (e) => {
    e.preventDefault();
    dropArea.classList.add('hover');
});

dropArea.addEventListener('dragleave', (e) => {
    e.preventDefault();
    dropArea.classList.remove('hover');
});

dropArea.addEventListener('dragover', (e) => {
    e.preventDefault();
});

dropArea.addEventListener('drop', (e) => {
    e.preventDefault();
    dropArea.classList.remove('hover');
    const files = e.dataTransfer.files;
    handleFiles(files);
});

fileInput.addEventListener('change', () => {
    const files = fileInput.files;
    handleFiles(files);
});

function handleFiles(files) {
    for (const file of files) {
        const listItem = document.createElement('div');
        listItem.classList.add('file-item');
        listItem.innerHTML = `
            <span>${file.name}</span>
            <span>${formatSize(file.size)}</span>
        `;
        fileList.appendChild(listItem);
    }
    if (files.length > 0) {
        uploadButton.removeAttribute('disabled');
    } else {
        uploadButton.setAttribute('disabled', 'true');
    }
}

function formatSize(bytes) {
    const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB'];
    if (bytes === 0) return '0 Byte';
    const i = parseInt(Math.floor(Math.log(bytes) / Math.log(1024)));
    return Math.round(bytes / Math.pow(1024, i), 2) + ' ' + sizes[i];
}






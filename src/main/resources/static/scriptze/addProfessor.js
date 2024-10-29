let tierList;
let grades;

let professors = [];

let preloadedProfessors = [
    {
        img: '/imgs/ianni.png',
        imgFile: false,
        name: 'Ianni',
        grade: 'S'
    },
    {
        img: '/imgs/dodaro.png',
        imgFile: false,
        name: 'Dodaro',
        grade: 'A'
    },
    {
        img: '/imgs/ricca.png',
        imgFile: false,
        name: 'Ricca',
        grade: 'A'
    },
    {
        img: '/imgs/perri.png',
        imgFile: false,
        name: 'Perri',
        grade: 'A'
    }
];

function loadProfessorHTML() {
    const grade = document.getElementById('gradeSelect');
    const name = document.getElementById('professorName');
    const img = document.getElementById('professorImg');

    if (!grades[grade.value]) {
        return false;
    }
    if (name.value === "") {
        name.focus();
        return false;
    }
    if (img.value === "") {
        img.focus();
        return false;
    }
    let prof = {
        grade: grade.value,
        name: name.value,
        img: img.value
    }
    professors.push(prof);
    loadProfessor(prof);
}


function loadProfessor(professor) {
    let grade = professor.grade;

    let gradeRow = grades[grade];
    const cell = gradeRow.querySelector('.professori');
    const img = document.createElement('img');
    img.src = professor.img;
    img.alt = professor.name;
    img.className = 'professor-img';
    img.title = professor.name;
    img.oncontextmenu = (e) => {
        e.preventDefault();
        img.style.display = 'none';
    }

    cell.appendChild(img);
}

window.onload = () => {
    grades = {
        S: document.getElementById('grade-S'),
        A: document.getElementById('grade-A'),
        B: document.getElementById('grade-B'),
        C: document.getElementById('grade-C'),
        D: document.getElementById('grade-D'),
        E: document.getElementById('grade-E'),
        F: document.getElementById('grade-F'),
    };

    tierList = document.getElementById('tierTableBody');

    for (let professor of preloadedProfessors) {
        loadProfessor(professor);
    }

    let form = document.getElementById("addProf");
    function handleForm(event) { event.preventDefault(); }
    form.addEventListener('submit', handleForm);
}
/**
 * 
 */

function MKSOLeditbuttoninvisible() {
    const displayInfoForm = document.getElementById("MKSOLdisplayinfoform");
    const editForm = document.getElementById("MKSOLeditform");

    editForm.classList.remove("invisible");
    editForm.classList.add("visible");

    displayInfoForm.classList.remove("visible");
    displayInfoForm.classList.add("invisible");
}

function MKSOLeditcancelvisible() {
    const displayInfoForm = document.getElementById("MKSOLdisplayinfoform");
    const editForm = document.getElementById("MKSOLeditform");

    displayInfoForm.classList.remove("invisible");
    displayInfoForm.classList.add("visible");

    editForm.classList.remove("visible");
    editForm.classList.add("invisible");
}


function MKSOLeditpronunciationbuttoninvisible() {
    const displayInfoForm = document.getElementById("MKSOLdisplaypronunciationform");
    const editForm = document.getElementById("MKSOLeditpronunciationform");

    editForm.classList.remove("invisible");
    editForm.classList.add("visible");

    displayInfoForm.classList.remove("visible");
    displayInfoForm.classList.add("invisible");
}

function MKSOLeditpronunciationcancelvisible() {
    const displayInfoForm = document.getElementById("MKSOLdisplaypronunciationform");
    const editForm = document.getElementById("MKSOLeditpronunciationform");

    displayInfoForm.classList.remove("invisible");
    displayInfoForm.classList.add("visible");

    editForm.classList.remove("visible");
    editForm.classList.add("invisible");
}

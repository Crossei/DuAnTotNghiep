var currentTab = 0;
showTab(currentTab);


function showTab(n) {

    var x = document.getElementsByClassName("tab");
    x[n].style.display = "block";

    if (n == 0) {
        document.getElementById("prevBtn").style.display = "none";
        document.getElementById("prevBtn").style.color = "#ffffff";
        document.getElementById("prevBtn").style.fontFamily = "UVN Nhan Nang";
        document.getElementById("prevBtn").style.fontStyle = "oblique";
        document.getElementById("prevBtn").style.backgroundColor = "#ed7b7b";

    } else {
        document.getElementById("prevBtn").style.display = "inline";
        document.getElementById("prevBtn").style.color = "#ffffff";
        document.getElementById("prevBtn").style.fontFamily = "UVN Nhan Nang";
        document.getElementById("prevBtn").style.fontStyle = "oblique";
        document.getElementById("prevBtn").style.backgroundColor = "#ed7b7b";
    }
    if (n == (x.length - 1)) {
        document.getElementById("nextBtn").style.display = "none";
        document.getElementById("nextBtn").style.color = "#ffffff";
        // document.getElementById("nextBtn").innerHTML = "Đặt ngay!";
        document.getElementById("nextBtn").style.fontFamily = "UVN Nhan Nang";
        document.getElementById("nextBtn").style.fontStyle = "oblique";
        document.getElementById("nextBtn").style.backgroundColor = "#7baded";

    } else {
        document.getElementById("nextBtn").style.display = "inline";
        document.getElementById("nextBtn").innerHTML = "Tiếp tục";
        document.getElementById("nextBtn").style.color = "#ffffff";
        document.getElementById("nextBtn").style.fontFamily = "UVN Nhan Nang";
        document.getElementById("nextBtn").style.fontStyle = "oblique";
        document.getElementById("nextBtn").style.backgroundColor = "#7baded";
    }

    fixStepIndicator(n)
}

function fixStepIndicator(n) {

    var i, x = document.getElementsByClassName("step");
    for (i = 0; i < x.length; i++) {
        x[i].className = x[i].className.replace(" active", "");
    }

    x[n].className += " active";
}


function nextPrev(n) {

    var x = document.getElementsByClassName("tab");

    if (n == 0 && !validateForm()) return false;

    x[currentTab].style.display = "none";

    currentTab = currentTab + n;

    showTab(currentTab);
}

function validateForm() {

    var x, y, i, valid = true;
    x = document.getElementsByClassName("tab");
    y = x[currentTab].getElementsByTagName("input");
    // A loop that checks every input field in the current tab:
    for (i = 0; i < y.length; i++) {
        // If a field is empty...
        if (y[i].checked) {
            // add an "invalid" class to the field:
            alert("Vui lòng chọn")
                // and set the current valid status to false
            valid = false;
        }
    }
    // If the valid status is true, mark the step as finished and valid:
    if (valid) {
        alert("suc");
    }
    return valid; // return the valid status
}
// JavaScript to toggle the dropdown
function toggleDropdown(event) {
    event.stopPropagation();
    const dropdownContent = event.currentTarget.nextElementSibling;
    dropdownContent.classList.toggle('show');
}

// Add event listeners to all dropdown buttons
document.querySelectorAll('.dropdownButton').forEach(button => {
    button.addEventListener('click', toggleDropdown);
});

// Close the dropdown if the user clicks outside of it
window.onclick = function(event) {
    if (!event.target.closest('.dropdownButton')) {
        document.querySelectorAll('.dropdownContent').forEach(content => {
            content.classList.remove('show');
        });
    }
}
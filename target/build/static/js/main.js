/* Common Glassmorphism Web App Scripts */

document.addEventListener('DOMContentLoaded', () => {
    // Alert auto-dismiss
    const alerts = document.querySelectorAll('.alert-dismissible');
    alerts.forEach(alert => {
        setTimeout(() => {
            alert.style.opacity = '0';
            setTimeout(() => alert.remove(), 300);
        }, 4000);
    });
});

/**
 * Filter table rows dynamically based on search input value.
 * @param {string} inputId - The ID of the search text input.
 * @param {string} tableId - The ID of the table to filter.
 */
function filterTable(inputId, tableId) {
    const input = document.getElementById(inputId);
    const table = document.getElementById(tableId);
    if (!input || !table) return;

    const filter = input.value.toLowerCase().trim();
    const rows = table.querySelectorAll('tbody tr');

    rows.forEach(row => {
        const text = row.textContent.toLowerCase();
        if (text.includes(filter)) {
            row.style.display = '';
        } else {
            row.style.display = 'none';
        }
    });
}

document.addEventListener("DOMContentLoaded", () => {
    console.log("The script is calling.")
    setTimeout(()=>{
        console.log("CHECKING Its coming or not 222 ")
    },1000)
    const element = document.querySelector(".passwordClass");
    if (element) {
        element.addEventListener("blur", () => {
            const event = new Event("change", { bubbles: true });
            element.dispatchEvent(event);
        });
    }
});

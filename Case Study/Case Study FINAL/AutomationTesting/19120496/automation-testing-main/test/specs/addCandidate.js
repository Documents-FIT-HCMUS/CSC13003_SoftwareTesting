const candidates = require("../data/candidates.json");
const fs = require('fs');

const loginUrl = 'http://localhost/orangehrm-5.1/web/index.php/auth/login';
const addCandidateUrl = 'http://localhost/orangehrm-5.1/web/index.php/recruitment/addCandidate'
describe("Add Job", async () => {
    before('Login', async() => {
        await browser.url(loginUrl);

        const usernameInput = await $('input[name="username"]');
        const passwordInput = await $('input[name="password"]');

        await usernameInput.setValue('KDuyen');
        await passwordInput.setValue('Password1@');

        const loginButton = await $('button[type="submit"]');
        await loginButton.click();

        browser.pause(10000);
    })

    after('Save result', async () => {
        fs.writeFile("D:\\Coding\\Testing\\selenium\\test\\result\\addCandidateResult.json", JSON.stringify(candidates), err => {
            if (err) {
                throw err
            }
        })
    })

    beforeEach('Navigate to add job url', async () => {
        browser.url(addCandidateUrl);

        browser.pause(10000);
    })

    // afterEach('Navigate to add job url', async () => {

    // })


    candidates.map((candidate, index) => {
        console.log('DEBUG JOB:', candidate);

        return it(`Adding candidate #${index + 1}`, async () => {
            const saveButton = await $('.oxd-button.oxd-button--medium.oxd-button--secondary.orangehrm-left-space');
            const inputs = await $$('.oxd-input.oxd-input--active');

            const firstName = inputs[1];
            const middleName = inputs[2];
            const lastName = inputs[3];

            const email = inputs[4];
            const contactNumber = inputs[5];

            firstName.setValue(candidate.firstname);
            middleName.setValue(candidate.middlename);
            lastName.setValue(candidate.lastname);

            email.setValue(candidate.email);
            contactNumber.setValue(candidate.contactNumber);

            await saveButton.waitForClickable({timeout: 5000});
            await saveButton.click();

            browser.pause(10000);

            const errElemets = await $$('.oxd-text.oxd-text--span.oxd-input-field-error-message.oxd-input-group__message');
            candidate.actualOutput = `There are ${errElemets.length} error field`;
        })
    })
})
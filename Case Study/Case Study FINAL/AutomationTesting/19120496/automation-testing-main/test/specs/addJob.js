const jobs = require("../data/jobs.json");
const fs = require('fs');

const loginUrl = 'http://localhost/orangehrm-5.1/web/index.php/auth/login';
const addJobUrl = 'http://localhost/orangehrm-5.1/web/index.php/admin/saveJobTitle'
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
        fs.writeFile("D:\\Coding\\Testing\\selenium\\test\\result\\addJobResult.json", JSON.stringify(jobs), err => {
            if (err) {
                throw err
            }
            console.log('JSON data is saved.')
        })
    })

    beforeEach('Navigate to add job url', async () => {
        browser.url(addJobUrl);

        browser.pause(10000);
    })

    // afterEach('Navigate to add job url', async () => {

    // })


    jobs.map((job, index) => {
        console.log('DEBUG JOB:', job);

        return it(`Adding job #${index + 1}`, async () => {
            const saveButton = await $('.oxd-button.oxd-button--medium.oxd-button--secondary.orangehrm-left-space');

            const jobTitleInput = await $$('.oxd-input.oxd-input--active')[1];
            const jobDescriptionInput = await $$('textarea')[0];
            const jobNoteInput = await $$('textarea')[1];
            const jobFileInput = await $('.oxd-file-input');

            jobTitleInput.setValue(job.title);
            jobDescriptionInput.setValue(job.description);
            jobNoteInput.setValue(job.note);
            jobFileInput.setValue(job.file);

            //await browser.saveScreenshot(`./test/images/addJob#${index}.png`);
            await saveButton.waitForClickable({timeout: 5000});
            await saveButton.click();

            browser.pause(10000);

            // console.log('~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~');
            const errElemets = await $$('.oxd-text.oxd-text--span.oxd-input-field-error-message.oxd-input-group__message');
            job.actualOutput = `There are ${errElemets.length} error field`;
        })
    })
})
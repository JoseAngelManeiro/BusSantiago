
# 📝 Pull Request: Description

Please include a summary of the change and which issue is fixed.    
Explain the motivation and context behind the change.

- What problem does it solve?
- Why is this change needed?
- Which module(s) does it affect? (`app`, `core`, `shared`, `widget`)

If this PR introduces UI changes, **include screenshots** in the section below.


# 🔍 What Has Been Done?

- [ ] Feature implementation
- [ ] Bug fix
- [ ] Refactor
- [ ] Documentation update
- [ ] Other (please describe):

Provide a high-level overview of the changes here.


# 🧱 Module Boundaries Checklist

Please ensure you respect the architecture rules:

- [ ] No domain/data logic inside `app` module
- [ ] `core` module keeps implementation classes/internal visibility
- [ ] `shared` contains only reusable UI logic/resources
- [ ] `widget` remains isolated from main app presentation flow
- [ ] No new external dependencies added without justification
- [ ] If a dependency was added, it is included via `libs.versions.toml`


# 🔧 Technical Notes

(Optional)    
Add any relevant details about architectural decisions, edge cases, or alternative solutions you considered.


# 🧪 Testing

- [ ] I have tested the changes on a device/emulator
- [ ] The app builds successfully
- [ ] No regressions found in affected screens
- [ ] (Optional) Added or updated tests where applicable


# 📸 Screenshots / Videos (if UI changes)

| Before | After |  
|--------|--------|  
| _Insert image_ | _Insert image_ |  

Or add GIFs (recommended for interactive changes)

# ✅ Final Checklist Before Review

- [ ] PR title follows convention (e.g. `feat: ...`, `fix: ...`, `refactor: ...`)
- [ ] Commit history is clean (rebased if needed)
- [ ] Code adheres to project style guidelines
- [ ] All TODOs or debug logs removed
- [ ] No commented-out code left behind



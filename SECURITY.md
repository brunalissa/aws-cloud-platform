# Security Policy

## Supported Versions

| Version | Supported          |
|---------|------------------|
| main    | :white_check_mark: |

## Reporting a Vulnerability

If you discover a security vulnerability in this project, please report it responsibly.

1. **Do not open a public issue** for security vulnerabilities.
2. Email the maintainer at: brubsalmeida0@gmail.com
3. Include:
   - A description of the vulnerability
   - Steps to reproduce
   - Potential impact
   - Suggested fix (if any)

You will receive a response within 48 hours acknowledging the report.

## Security Practices in This Project

- No secrets, credentials, or private keys are committed to the repository
- AWS credentials are managed via GitHub OIDC (no long-lived access keys)
- Database credentials are stored in AWS Secrets Manager
- Terraform state is not committed to version control
- Container images are scanned with Trivy before deployment
- Dependencies are scanned for known vulnerabilities
- Infrastructure follows the principle of least privilege

## Disclosure Policy

Once a vulnerability is confirmed and fixed, a security advisory will be published with:
- A description of the vulnerability
- The affected versions
- The fix applied
- Credit to the reporter (with permission)

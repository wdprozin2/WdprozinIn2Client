import os
import sys
import json
import urllib.request

REPO = "wdprozin2/WdprozinIn2Client"
JAR_PATH = r"C:\Users\joaol\OneDrive\Desktop\Xynis&.1\Xynis.jar"

def get_token():
    if os.environ.get("GITHUB_TOKEN"):
        return os.environ.get("GITHUB_TOKEN").strip()
    token_file = os.path.join(os.path.dirname(__file__), "github_token.txt")
    if os.path.exists(token_file):
        with open(token_file, "r") as f:
            return f.read().strip()
    raise ValueError("Token do GitHub não encontrado em GITHUB_TOKEN ou github_token.txt!")

def publish_release(tag="v6.2", title="WdprozinIn2Client v6.2", body="Release oficial do WdprozinIn2Client v6.2"):
    token = get_token()
    headers = {
        "Authorization": f"Bearer {token}",
        "Accept": "application/vnd.github.v3+json",
        "User-Agent": "Wdprozin-Release-Publisher"
    }

    import hashlib
    jar_sha256 = ""
    if os.path.exists(JAR_PATH):
        h = hashlib.sha256()
        with open(JAR_PATH, "rb") as f:
            while chunk := f.read(65536):
                h.update(chunk)
        jar_sha256 = h.hexdigest()

    full_body = body + f"\n\n### 🔒 Official Build Verification (SHA-256)\n```\n{jar_sha256}\n```\n\n> ℹ️ Verify authenticity by checking this hash before running."

    # 1. Create Release
    create_url = f"https://api.github.com/repos/{REPO}/releases"
    payload = {
        "tag_name": tag,
        "target_commitish": "main",
        "name": title,
        "body": full_body,
        "draft": False,
        "prerelease": False
    }
    req = urllib.request.Request(create_url, data=json.dumps(payload).encode('utf-8'), headers=headers, method="POST")
    try:
        with urllib.request.urlopen(req) as resp:
            data = json.loads(resp.read().decode('utf-8'))
            upload_url_template = data["upload_url"]
            upload_url = upload_url_template.split("{")[0]
            release_id = data["id"]
            html_url = data["html_url"]
            print(f"[OK] Release criada com sucesso: {html_url}")
    except urllib.error.HTTPError as e:
        err_msg = e.read().decode('utf-8')
        print(f"[ERRO] Falha ao criar release (HTTP {e.code}): {err_msg}")
        return False

    # 2. Upload Asset (.jar)
    if not os.path.exists(JAR_PATH):
        print(f"[ERRO] Arquivo JAR não encontrado em: {JAR_PATH}")
        return False

    file_size = os.path.getsize(JAR_PATH)
    print(f"Fazendo upload de {JAR_PATH} ({file_size / (1024*1024):.2f} MB)...")

    asset_name = "WdprozinIn2Client.jar"
    upload_target = f"{upload_url}?name={asset_name}"
    upload_headers = {
        "Authorization": f"Bearer {token}",
        "Content-Type": "application/java-archive",
        "Content-Length": str(file_size),
        "User-Agent": "Wdprozin-Release-Publisher"
    }

    with open(JAR_PATH, "rb") as f:
        upload_data = f.read()

    upload_req = urllib.request.Request(upload_target, data=upload_data, headers=upload_headers, method="POST")
    try:
        with urllib.request.urlopen(upload_req) as resp:
            asset_data = json.loads(resp.read().decode('utf-8'))
            print(f"[OK] Asset enviado com sucesso: {asset_data.get('browser_download_url')}")
            return True
    except urllib.error.HTTPError as e:
        err_msg = e.read().decode('utf-8')
        print(f"[ERRO] Falha no upload do asset (HTTP {e.code}): {err_msg}")
        return False

if __name__ == "__main__":
    tag = sys.argv[1] if len(sys.argv) > 1 else "v6.2"
    title = sys.argv[2] if len(sys.argv) > 2 else f"WdprozinIn2Client {tag}"
    publish_release(tag, title)

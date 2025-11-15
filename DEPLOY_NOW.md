# Deploy PluginForge Studio - Quick Start

## Instant Deployment to Render (10 Minutes)

### Step 1: Create GitHub Repository (2 minutes)

1. Go to https://github.com/new
2. Repository name: `pluginforge-studio`
3. Description: `AI-powered Minecraft plugin generator`
4. Public repository
5. **Do NOT initialize** with README
6. Click "Create repository"

### Step 2: Push Code to GitHub (2 minutes)

```bash
cd /workspace/PluginForge-Studio

# Initialize git (if not already done)
git init

# Add all files
git add .

# Commit
git commit -m "Production-ready PluginForge Studio v2.0"

# Add your GitHub repository
git remote add origin https://github.com/YOUR_USERNAME/pluginforge-studio.git

# Push
git branch -M main
git push -u origin main
```

### Step 3: Deploy to Render (5 minutes)

1. **Go to**: https://dashboard.render.com
2. **Click**: "New +" → "Blueprint"
3. **Connect**: Your GitHub repository `pluginforge-studio`
4. **Render will detect** `render.yaml` and automatically:
   - Create PostgreSQL database named `pluginforge-db`
   - Create web service named `pluginforge-studio`
   - Set up most environment variables
5. **Add OPENROUTER_API_KEY**:
   - In the Blueprint review screen
   - Set `OPENROUTER_API_KEY` to:
     ```
     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
     ```
6. **Click**: "Apply"
7. **Wait**: 3-5 minutes for deployment

### Step 4: Verify Deployment (1 minute)

Your app will be live at: `https://pluginforge-studio.onrender.com`

Test:
- Homepage loads
- Login with: `admin` / `admin123`
- Create a test plugin to verify AI integration

---

## Alternative: Manual Deployment

If Blueprint doesn't work, use manual setup:

### A. Create PostgreSQL Database

1. Dashboard → "New +" → "PostgreSQL"
2. Name: `pluginforge-db`
3. Database: `pluginforge`
4. Plan: Free
5. Create → Copy **Internal Database URL**

### B. Create Web Service

1. Dashboard → "New +" → "Web Service"
2. Connect GitHub repo
3. Settings:
   - **Name**: `pluginforge-studio`
   - **Runtime**: Python 3
   - **Build**: `pip install -r requirements-prod.txt && python init_db.py`
   - **Start**: `gunicorn app:app -c gunicorn_config.py`

4. Environment variables:
   ```
   RENDER=true
   PYTHON_VERSION=3.12.0
   OPENROUTER_API_KEY=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
   SECRET_KEY=<click "Generate" button>
   DATABASE_URL=<paste from PostgreSQL Internal URL>
   ```

5. Click "Create Web Service"

---

## Post-Deployment

### 1. Change Admin Password
- Login: `admin` / `admin123`
- Go to Profile → Change password

### 2. Test AI Generation
- Click "New Plugin"
- Enter: "Create a welcome plugin"
- Verify plugin generates successfully

### 3. Monitor Logs
- Dashboard → Service → Logs
- Look for: `✅ Database tables created successfully`

---

## Troubleshooting

### Database Not Initialized

If you see errors about missing tables:

1. Go to: Dashboard → Your Service → Shell
2. Run: `python init_db.py`
3. Should see: `✅ Database tables created successfully`

### API Key Error

If plugin generation fails:
- Verify `OPENROUTER_API_KEY` is set correctly
- Check: Dashboard → Service → Environment

---

## All Files Ready

These files are already configured for production:

- `render.yaml` - Automatic deployment config
- `requirements-prod.txt` - Production dependencies
- `gunicorn_config.py` - Production server config
- `init_db.py` - Database initialization
- `app.py` - Main Flask application (auto-detects Render)
- `models.py` - Database models
- `.gitignore` - Git ignore rules

---

## Support

- Render Docs: https://render.com/docs
- Deployment Guide: See DEPLOYMENT.md
- Render Support: https://render.com/support

---

**Total Time**: ~10 minutes
**Status**: Ready to deploy
**Cost**: $0 (Free tier)

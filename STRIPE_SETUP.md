# Stripe Payment Integration Guide

## Overview

PluginForge Studio now includes full Stripe payment integration for Pro and Premium subscription plans.

## Features

- Real Stripe Checkout for subscription payments
- Webhook handling for payment confirmations
- Automatic subscription activation
- Demo mode fallback when Stripe not configured
- Subscription cancellation support

## Setup Instructions

### 1. Create Stripe Account

1. Go to https://stripe.com/
2. Sign up for a free account
3. Verify your email and business information

### 2. Get API Keys

1. Go to https://dashboard.stripe.com/apikeys
2. Copy your **Publishable key** and **Secret key**
3. For testing, use test mode keys (starts with `pk_test_` and `sk_test_`)

### 3. Create Products and Prices

#### Create Pro Plan Product
1. Go to https://dashboard.stripe.com/products/create
2. Name: "PluginForge Pro"
3. Description: "50 plugins per month with advanced features"
4. Pricing: $9.00 USD / month (recurring)
5. Click "Save product"
6. Copy the **Price ID** (starts with `price_`)

#### Create Premium Plan Product
1. Go to https://dashboard.stripe.com/products/create
2. Name: "PluginForge Premium"
3. Description: "Unlimited plugins with priority support"
4. Pricing: $19.00 USD / month (recurring)
5. Click "Save product"
6. Copy the **Price ID** (starts with `price_`)

### 4. Set Up Webhook

1. Go to https://dashboard.stripe.com/webhooks
2. Click "Add endpoint"
3. Endpoint URL: `https://pluginforge-studio.onrender.com/api/stripe/webhook`
4. Select events to listen to:
   - `checkout.session.completed`
   - `customer.subscription.deleted`
   - `customer.subscription.updated`
5. Click "Add endpoint"
6. Copy the **Webhook signing secret** (starts with `whsec_`)

### 5. Configure Environment Variables in Render

Add these to your Render service environment variables:

```
STRIPE_SECRET_KEY=sk_test_... (or sk_live_... for production)
STRIPE_PUBLISHABLE_KEY=pk_test_... (or pk_live_... for production)
STRIPE_WEBHOOK_SECRET=whsec_...
STRIPE_PRICE_PRO=price_... (Pro plan price ID)
STRIPE_PRICE_PREMIUM=price_... (Premium plan price ID)
```

## How It Works

### Payment Flow

1. **User clicks "Upgrade"** on plans page
2. **Backend creates Stripe Checkout Session**
   - Creates/retrieves Stripe customer
   - Generates secure checkout URL
   - Returns URL to frontend
3. **User redirected to Stripe Checkout**
   - Secure Stripe-hosted payment page
   - Collects payment information
   - Processes payment
4. **Payment completion**
   - User redirected back to success page
   - Webhook notifies backend
   - Subscription activated in database

### Demo Mode

If Stripe keys are not configured, the system falls back to demo mode:
- Instant subscription activation
- No payment processing
- Useful for testing and development
- Warning shown to user

## Testing

### Test Cards (Stripe Test Mode)

Use these card numbers in test mode:

- **Success**: `4242 4242 4242 4242`
- **Decline**: `4000 0000 0000 0002`
- **3D Secure**: `4000 0027 6000 3184`

Use any future expiration date and any 3-digit CVC.

### Testing Webhooks Locally

Install Stripe CLI:
```bash
stripe listen --forward-to localhost:5002/api/stripe/webhook
```

## Security

- Webhook signature verification prevents fraud
- Stripe handles all payment data (PCI compliant)
- No credit card info stored in our database
- SSL/TLS encryption for all connections

## Going Live

### Before Launching

1. **Switch to Live Mode** in Stripe Dashboard
2. **Update API keys** to live keys (`sk_live_`, `pk_live_`)
3. **Create live products** and update Price IDs
4. **Update webhook** with live endpoint URL
5. **Test with real card** (use small amount)
6. **Monitor webhook** activity

### Production Checklist

- [ ] Live Stripe API keys configured
- [ ] Live product prices created
- [ ] Webhook configured for production URL
- [ ] Test successful payment flow
- [ ] Test webhook events
- [ ] Verify subscription activation
- [ ] Test subscription cancellation
- [ ] Monitor error logs

## Troubleshooting

### Payments not processing

1. Check Stripe API keys are correct
2. Verify Price IDs match Stripe Dashboard
3. Check webhook endpoint is accessible
4. Review Stripe Dashboard logs

### Webhook not working

1. Verify webhook secret is correct
2. Check webhook URL is publicly accessible
3. Test with Stripe CLI locally
4. Review webhook signature verification

### Subscription not activating

1. Check webhook events in Stripe Dashboard
2. Verify database updates in logs
3. Test checkout.session.completed event
4. Check user metadata in Stripe

## Support

- Stripe Documentation: https://stripe.com/docs
- Stripe Support: https://support.stripe.com/
- Test Mode: Use test API keys for safe testing

## Pricing

Stripe charges **2.9% + $0.30** per successful transaction.

For $9 Pro plan: ~$0.56 fee (you receive ~$8.44)
For $19 Premium plan: ~$0.85 fee (you receive ~$18.15)

## API Reference

### Create Checkout Session
```
POST /api/subscription/upgrade
Body: { "plan": "pro" | "premium" }
Returns: { "success": true, "checkout_url": "https://checkout.stripe.com/..." }
```

### Success Callback
```
GET /subscription/success?session_id=...
Verifies payment and activates subscription
```

### Webhook Handler
```
POST /api/stripe/webhook
Handles: checkout.session.completed, customer.subscription.deleted
```

## Notes

- Basic plan is free (no Stripe payment)
- Subscriptions are monthly recurring
- Users can cancel anytime
- Prorated refunds can be configured in Stripe

---

**Version**: 1.0
**Last Updated**: 2025-11-15
**Status**: Production Ready
